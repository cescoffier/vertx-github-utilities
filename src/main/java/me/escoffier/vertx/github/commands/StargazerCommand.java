package me.escoffier.vertx.github.commands;

import com.beust.jcommander.Parameter;
import com.google.common.collect.ImmutableList;
import me.escoffier.vertx.github.model.Stargazer;
import me.escoffier.vertx.github.release.Project;
import me.escoffier.vertx.github.services.StargazerService;
import me.escoffier.vertx.github.utils.TableOfInt;
import me.escoffier.vertx.github.utils.TableOfSet;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class StargazerCommand extends AbstractCommand {

  @Parameter(names = {"--repositories", "-r"},
      required = true,
      description = "The json file listing the repositories")
  private File repositories;

  public void run() {
    assert repositories.exists();

    List<Project> projects = Project.load(repositories);
    logger.info(projects.size() + " projects loaded");
    logger.debug("Parsed projects: " + projects.stream().map(Project::id).collect(Collectors.toList()));

    List<Stargazer> stargazers = retrieveStargazers(projects);

    List<Stargazer> sorted = new ArrayList<>(stargazers);
    sorted.sort((s1, s2) -> {
      Date date1 = s1.toDate();
      Date date2 = s2.toDate();
      return date1.compareTo(date2);
    });
    try {

      // Build a table storing the set of stargazer acquired every month per repository
      TableOfSet<String> acquiredStargazersPerMonthPerRepo = new TableOfSet<>(computeColumnNamesForStargazers(projects));
      for (Stargazer stargazer : sorted) {
        String key = key(stargazer);
        acquiredStargazersPerMonthPerRepo.add(key, stargazer.getProject(), stargazer.getUser().getLogin());
      }
      acquiredStargazersPerMonthPerRepo.fill(Collections.emptySet());

      TableOfInt stargazersPerRepository = computeStargazerData(projects, acquiredStargazersPerMonthPerRepo).fill(0);
      TableOfInt stargazersConsolidated = computeConsolidatedView(projects, acquiredStargazersPerMonthPerRepo).fill(0);

      File raw = new File("stargazers.csv");
      File consolidated = new File("stargazers-consolidated.csv");
      logger.info("Writing stargazers per month per repo data to " + raw.getAbsolutePath());
      stargazersPerRepository.toCSV(raw);
      logger.info("Writing stargazers consolidated view to " + consolidated.getAbsolutePath());
      stargazersConsolidated.toCSV(consolidated);
    } catch (Exception e) {
      fatal("Unable to retrieve / analyze / dump stargazers", e);
    }
  }

  private TableOfInt computeConsolidatedView(List<Project> projects, TableOfSet<String> acquiredStargazersPerMonthPerRepo) {
    TableOfInt table = new TableOfInt(ImmutableList.of(
        "Month",
        "Number of stars acquired",
        "Number of stargazers acquired",
        "Number of stars",
        "Number of unique stargazers"));
    int stars = 0;
    Set<String> stargazers = new HashSet<>();
    for (String date : acquiredStargazersPerMonthPerRepo.getTable().keySet()) {
      Set<String> acquired = new HashSet<>();
      int acquiredCount = 0;
      for (Project project : projects) {
        String id = project.id();
        Set<String> names = acquiredStargazersPerMonthPerRepo.get(date, id);
        acquiredCount += names.size();
        acquired.addAll(names);
      }

      stars += acquiredCount;
      stargazers.addAll(acquired);

      table.put(date, "Number of stars acquired", acquiredCount);
      table.put(date, "Number of stargazers acquired", acquired.size());
      table.put(date, "Number of stars", stars);
      table.put(date, "Number of unique stargazers", stargazers.size());
    }

    return table;
  }

  private List<Stargazer> retrieveStargazers(List<Project> projects) {
    assert token != null;
    List<Stargazer> stargazers = new ArrayList<>();
    List<String> users = new ArrayList<>();
    projects.forEach(project -> {
      try {
        Set<Stargazer> result = StargazerService.stargazers(project, token);
        stargazers.addAll(result);
        users.addAll(result.stream().map(s -> s.getUser().getLogin()).collect(Collectors.toList()));

      } catch (IOException e) {
        fatal("Cannot retrieve the stargazers for " + project.id(), e);
      }
    });

    logger.info("Number of stargazers: " + users.size());
    logger.info("Number of (unique) stargazers: " + new HashSet<>(users).size());

    return stargazers;
  }

  private TableOfInt computeStargazerData(List<Project> projects, TableOfSet<String> acquiredStargazersPerMonthPerRepo) {
    TableOfInt table = new TableOfInt(computeColumnNamesForRawData(projects));

    for (Project project : projects) {
      int stars = 0;
      for (String date : acquiredStargazersPerMonthPerRepo.getTable().keySet()) {
        Map<String, Set<String>> line = acquiredStargazersPerMonthPerRepo.getLine(date);
        Set<String> acquired = line.get(project.id());
        stars += acquired.size();

        table.put(date, project.id() + " - Number of stargazers acquired", acquired.size());
        table.put(date, project.id() + " - Total number of stargazers", stars);
      }
    }

    return table;
  }

  private List<String> computeColumnNamesForRawData(List<Project> projects) {
    List<String> columns = new ArrayList<>();
    columns.add("Month");
    for (Project project : projects) {
      columns.add(project.id() + " - Number of stargazers acquired");
      columns.add(project.id() + " - Total number of stargazers");
    }
    return columns;
  }

  private List<String> computeColumnNamesForStargazers(List<Project> projects) {
    List<String> columns = new ArrayList<>();
    columns.add("date");
    for (Project project : projects) {
      columns.add(project.id());
    }
    return columns;
  }

  private String key(Stargazer stargazer) {
    Date date = stargazer.toDate();
    Calendar instance = Calendar.getInstance();
    instance.setTime(date);
    return (instance.get(Calendar.MONTH) + 1) + "-" + instance.get(Calendar.YEAR);
  }
}
