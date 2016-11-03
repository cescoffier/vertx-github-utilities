package me.escoffier.vertx.github.commands;

import com.beust.jcommander.Parameter;
import com.google.common.collect.ImmutableList;
import me.escoffier.vertx.github.model.Issue;
import me.escoffier.vertx.github.release.Project;
import me.escoffier.vertx.github.services.IssueService;
import me.escoffier.vertx.github.utils.TableOfInt;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class IssuesCommand extends AbstractCommand {

  @Parameter(names = {"--repositories", "-r"},
      required = true,
      description = "The json file listing the repositories")
  private File repositories;

  public void run() {
    // TODO replace assert
    if (!repositories.isFile()) {
      throw new IllegalArgumentException(repositories.getAbsolutePath() + " must be a valid file");
    }

    List<Project> projects = Project.load(repositories);
    logger.info(projects.size() + " projects loaded");
    logger.debug("Parsed projects: " + projects.stream().map(Project::id).collect(Collectors.toList()));

    List<Issue> issues = retrieveIssues(projects);

    List<Issue> sorted = new ArrayList<>(issues);
    sorted.sort((s1, s2) -> {
      Date date1 = s1.toDate();
      Date date2 = s2.toDate();
      return date1.compareTo(date2);
    });

    try {
      TableOfInt issuesPerRepository = computeIssueData(projects, sorted).fill(0);
      TableOfInt issuesConsolidated = computeConsolidatedView(projects, issuesPerRepository).fill(0);

      File raw = new File("issues.csv");
      File consolidated = new File("issues-consolidated.csv");
      logger.info("Writing issues per month per repo data to " + raw.getAbsolutePath());
      issuesPerRepository.toCSV(raw);
      logger.info("Writing issues consolidated view to " + consolidated.getAbsolutePath());
      issuesConsolidated.toCSV(consolidated);
    } catch (Exception e) {
      fatal("Unable to retrieve / analyze / dump issues", e);
    }
  }

  private TableOfInt computeConsolidatedView(List<Project> projects, TableOfInt issuesPerRepository) {
    TableOfInt table = new TableOfInt(ImmutableList.of(
        "Month",
        "Number of issues opened",
        "Number of issues closed"));

    for (String key : issuesPerRepository.getTable().keySet()) {
      int open = 0;
      int close = 0;
      for (Project project : projects) {
        open += issuesPerRepository.get(key, project.id() + " - Number of issues opened");
        close += issuesPerRepository.get(key, project.id() + " - Number of issues closed");
      }

      table.put(key, "Number of issues opened", open);
      table.put(key, "Number of issues closed", close);
    }

    return table;
  }

  private List<Issue> retrieveIssues(List<Project> projects) {
    Objects.requireNonNull(token);
    List<Issue> issues = new ArrayList<>();

    projects.forEach(project -> {
      try {
        Set<Issue> set = IssueService.issues(project, token, new IssueService.Query().descending().sort("created").state("all"));
        logger.info("Number of issues for " + project.id() + " : " + set.size() + " (" + set.stream().filter
            (issue -> issue.getState().equalsIgnoreCase("open")).count() + " open issues)");
        issues.addAll(set);
      } catch (IOException e) {
        fatal("Cannot retrieve the issues for " + project.id(), e);
      }
    });

    logger.info("Number of issues: " + issues.size());
    long open = issues.stream().filter(issue -> issue.getState().equalsIgnoreCase("open")).count();
    logger.info("Number of open issues: " + open);

    return issues;
  }

  private TableOfInt computeIssueData(List<Project> projects, List<Issue> issues) {
    TableOfInt table = new TableOfInt(computeColumnNamesForRepositories(projects));

    Set<String> keys = getMonthsSinceTheFirstIssue(issues);
    for (Project project : projects) {
      keys.forEach(s -> {
        table.put(s, project.id() + " - Number of issues opened", 0);
        table.put(s, project.id() + " - Number of issues closed", 0);
      });
    }

    for (Issue issue : issues) {
      String proj = issue.getProject();
      table.inc(key(issue.getCreationDate()), proj + " - Number of issues opened");
      if (issue.getClosedDate() != null) {
        table.inc(key(issue.getCreationDate()), proj + " - Number of issues closed");
      }
    }

    return table;
  }

  private Set<String> getMonthsSinceTheFirstIssue(List<Issue> issues) {
    if (issues.isEmpty()) {
      return Collections.emptySet();
    } else {
      Set<String> periods = new LinkedHashSet<>();

      Issue first = issues.get(0);

      Date currentDate = first.toDate();
      String currentKey = key(first.toDate());
      Date last = new Date();
      String lastKey = key(last);

      while (!lastKey.equalsIgnoreCase(currentKey)) {
        periods.add(currentKey);
        currentDate = incrementByOneMonth(currentDate);
        currentKey = key(currentDate);
      }

      periods.add(lastKey);

      return periods;
    }
  }

  private Date incrementByOneMonth(Date date) {
    Calendar instance = Calendar.getInstance();
    instance.setTime(date);
    instance.add(Calendar.MONTH, 1);
    return instance.getTime();
  }

  private List<String> computeColumnNamesForRepositories(List<Project> projects) {
    List<String> columns = new ArrayList<>();
    columns.add("date");
    for (Project project : projects) {
      columns.add(project.id() + " - Number of issues opened");
      columns.add(project.id() + " - Number of issues closed");
    }
    return columns;
  }

  private String key(Date date) {
    Calendar instance = Calendar.getInstance();
    instance.setTime(date);
    return (instance.get(Calendar.MONTH) + 1) + "-" + instance.get(Calendar.YEAR);
  }

  private String key(String date) {
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
    try {
      return key(format.parse(date));
    } catch (ParseException e) {
      throw new RuntimeException("Cannot parse date : " + date);
    }
  }
}
