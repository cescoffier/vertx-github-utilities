package me.escoffier.vertx.github.commands;

import com.beust.jcommander.Parameter;
import com.google.common.collect.ImmutableList;
import me.escoffier.vertx.github.model.Contributor;
import me.escoffier.vertx.github.model.GitCommit;
import me.escoffier.vertx.github.model.User;
import me.escoffier.vertx.github.release.Project;
import me.escoffier.vertx.github.services.ContributorService;
import me.escoffier.vertx.github.services.RepositoryService;
import me.escoffier.vertx.github.utils.TableOfInt;
import me.escoffier.vertx.github.utils.TableOfSet;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class ContributorCommand extends AbstractCommand {

  @Parameter(names = {"--repositories", "-r"},
      required = true,
      description = "The json file listing the repositories")
  private File repositories;

  @Override
  public void run() throws IOException {
    Objects.requireNonNull(token);

    assert repositories.exists();
    List<Project> projects = Project.load(repositories);

    logger.info(projects.size() + " projects loaded");
    logger.debug("Parsed projects: " + projects.stream().map(Project::id).collect(Collectors.toList()));

    // Compute the number of contributors (unique)
    List<String> users = new ArrayList<>();
    projects.forEach(project -> {
      try {
        Set<Contributor> result = ContributorService.contributors(project, token);
        users.addAll(result.stream().map(User::getLogin).collect(Collectors.toList()));
      } catch (IOException e) {
        fatal("Cannot retrieve the contributors for " + project.id(), e);
      }
    });

    logger.info("Number of contributors: " + users.size());
    logger.info("Number of (unique) contributors: " + new HashSet<>(users).size());

    // Hard work happen here...
    logger.info("Computing contribution data (grab a cup of coffee....)");
    List<GitCommit> fullLog = retrieveAllCommits(projects);

    // Build a table count the commits per month per project
    TableOfInt commitPerMonthPerRepo = new TableOfInt(computeColumnNamesForContributorTable(projects));
    // Build a table keeping track of the contributor per month per project
    TableOfSet<String> contributorPerMonthPerRepo
        = new TableOfSet<>(computeColumnNamesForContributorTable(projects));

    for (GitCommit commit : fullLog) {
      String key = key(commit.toDate());
      String repo = commit.getProject();
      // Keep track of the contributor
      contributorPerMonthPerRepo.add(key, repo, commit.getContributor());
      // Keep track of the commit
      commitPerMonthPerRepo.inc(key, repo);
    }

    commitPerMonthPerRepo.fill(0);
    contributorPerMonthPerRepo.fill(Collections.emptySet());

    TableOfInt contributionsPerRepository = computeContributionPerRepository(projects, commitPerMonthPerRepo,
        contributorPerMonthPerRepo).fill(0);
    TableOfInt contributionsConsolidated = computeConsolidatedView(projects, commitPerMonthPerRepo,
        contributorPerMonthPerRepo).fill(0);

    File raw = new File("contributions.csv");
    File consolidated = new File("contributions-consolidated.csv");
    logger.info("Writing contributions per month per repo data to " + raw.getAbsolutePath());
    contributionsPerRepository.toCSV(raw);
    logger.info("Writing contributions consolidated view to " + consolidated.getAbsolutePath());
    contributionsConsolidated.toCSV(consolidated);
  }

  private TableOfInt computeConsolidatedView(List<Project> projects, TableOfInt commitPerMonthPerRepo,
                                             TableOfSet<String> contributorPerMonthPerRepo) {
    TableOfInt table = new TableOfInt(ImmutableList.of(
        "Month",
        "Number of contributors",
        "Number of commits",
        "Cumulated number of contributors",
        "Cumulated number of commits"));

    int commits = 0;
    Set<String> contributors = new HashSet<>();
    for (String date : commitPerMonthPerRepo.getTable().keySet()) {
      Set<String> periodContributors = new HashSet<>();
      int perodCommits = 0;

      for (Project project : projects) {
        String id = project.id();
        Set<String> names = contributorPerMonthPerRepo.get(date, id);
        periodContributors.addAll(names);
        perodCommits += commitPerMonthPerRepo.get(date, id);
      }

      commits += perodCommits;
      contributors.addAll(periodContributors);

      table.put(date, "Number of contributors", periodContributors.size());
      table.put(date, "Number of commits", perodCommits);
      table.put(date, "Cumulated number of contributors", contributors.size());
      table.put(date, "Cumulated number of commits", commits);
    }

    return table;
  }

  private TableOfInt computeContributionPerRepository(List<Project> projects, TableOfInt commitPerMonthPerRepo, TableOfSet<String> contributorsPerMonthPerRepo) {
    TableOfInt table = new TableOfInt(computeColumnNamesForRawData(projects));

    for (Project project : projects) {
      int commits = 0;
      Set<String> committers = new HashSet<>();
      for (String date : commitPerMonthPerRepo.getTable().keySet()) {
        Map<String, Set<String>> lineContrib = contributorsPerMonthPerRepo.getLine(date);
        Map<String, Integer> lineCommit = commitPerMonthPerRepo.getLine(date);
        Set<String> contributors = lineContrib.get(project.id());
        int numberOfCommits = lineCommit.get(project.id());
        commits += numberOfCommits;
        committers.addAll(contributors);

        table.put(date, project.id() + " - Number of contributors", contributors.size());
        table.put(date, project.id() + " - Number of commits", numberOfCommits);
        table.put(date, project.id() + " - Total number of contributors", committers.size());
        table.put(date, project.id() + " - Total number of commits", commits);
      }
    }

    return table;
  }

  private List<String> computeColumnNamesForRawData(List<Project> projects) {
    List<String> columns = new ArrayList<>();
    columns.add("Month");
    for (Project project : projects) {
      columns.add(project.id() + " - Number of contributors");
      columns.add(project.id() + " - Number of commits");
      columns.add(project.id() + " - Total number of contributors");
      columns.add(project.id() + " - Total number of commits");
    }
    return columns;
  }

  private List<GitCommit> retrieveAllCommits(List<Project> projects) {
    List<GitCommit> fullLog = new ArrayList<>();
    for (Project project : projects) {
      logger.info("Retrieving the log for " + project.id());
      try {
        Set<GitCommit> log = RepositoryService.log(project, token);
        logger.info("Number of commits for " + project.id() + " : " + log.size());
        fullLog.addAll(log);
      } catch (IOException e) {
        fatal("Unable to retrieve the log for " + project.id(), e);
      }
    }

    fullLog.sort((s1, s2) -> {
      Date date1 = s1.toDate();
      Date date2 = s2.toDate();
      return date1.compareTo(date2);
    });
    return fullLog;
  }

  private List<String> computeColumnNamesForContributorTable(List<Project> projects) {
    List<String> columns = new ArrayList<>();
    columns.add("date");
    for (Project project : projects) {
      columns.add(project.id());
    }
    return columns;
  }

  private String key(Date date) {
    Calendar instance = Calendar.getInstance();
    instance.setTime(date);
    return (instance.get(Calendar.MONTH) + 1) + "-" + instance.get(Calendar.YEAR);
  }
}
