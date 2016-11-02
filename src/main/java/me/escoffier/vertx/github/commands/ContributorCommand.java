package me.escoffier.vertx.github.commands;

import com.beust.jcommander.Parameter;
import com.google.common.collect.ImmutableList;
import me.escoffier.vertx.github.model.Contributor;
import me.escoffier.vertx.github.model.User;
import me.escoffier.vertx.github.release.Project;
import me.escoffier.vertx.github.services.ContributorService;

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

  @Parameter(names = "score", description = "name of the score file (csv)")
  private File scoreFile = new File("scores.csv");

  @Parameter(names = "contribution", description = "name of the contribution per project file (csv)")
  private File contributionFile = new File("contributions.csv");

  @Override
  public void run() {
    assert repositories.exists();

    List<Project> projects = Project.load(repositories);
    logger.info(projects.size() + " projects loaded");
    logger.debug("Parsed projects: " + projects.stream().map(Project::id).collect(Collectors.toList()));

    // Compute the number of contributors (unique)
    List<Contributor> contributors = new ArrayList<>();
    List<String> users = new ArrayList<>();
    projects.forEach(project -> {
      try {
        Set<Contributor> result = ContributorService.contributors(project);
        contributors.addAll(result);
        users.addAll(result.stream().map(User::getLogin).collect(Collectors.toList()));
      } catch (IOException e) {
        fatal("Cannot retrieve the contributors for " + project.id(), e);
      }
    });

    logger.info("Number of contributors: " + users.size());
    logger.info("Number of (unique) contributors: " + new HashSet<>(users).size());

    // Global ranking of contributors (among all projects)
    Map<String, Integer> map = computeGlobalContributorScores(contributors);
    List<Map.Entry<String, Integer>> top = new ArrayList<>(map.entrySet()).subList(0, 10);
    logger.info("Top contributors");
    top.forEach(entry -> logger.info(entry.getKey() + " (" + entry.getValue() + ")"));

    try {
      CSVUtils.write(scoreFile, ImmutableList.of("name", "score"),
          map.entrySet().stream()
              .map(entry -> ImmutableList.of(entry.getKey(), entry.getValue().toString()))
              .collect(Collectors.toList()));
    } catch (IOException e) {
      fatal(logger, "Unable to write the scores file", e);
    }
    map.clear();
    top.clear();

    // Compute the number of contributors / project
    map = computeContributorPerProject(contributors);
    top = new ArrayList<>(map.entrySet()).subList(0, 10);
    logger.info("Most contributed projects");
    top.forEach(entry -> logger.info(entry.getKey() + " (" + entry.getValue() + ")"));
    try {
      CSVUtils.write(contributionFile, ImmutableList.of("project", "contributors"),
          map.entrySet().stream()
              .map(entry -> ImmutableList.of(entry.getKey(), entry.getValue().toString()))
              .collect(Collectors.toList()));
    } catch (IOException e) {
      fatal(logger, "Unable to write the contribution file", e);
    }

  }

  private Map<String, Integer> computeGlobalContributorScores(List<Contributor> contributors) {
    Map<String, Integer> scores = new HashMap<>();
    contributors.forEach(c -> {
      Integer score = scores.get(c.getLogin());
      if (score == null) {
        scores.put(c.getLogin(), c.getContributions());
      } else {
        scores.put(c.getLogin(), score + c.getContributions());
      }
    });

    List<Map.Entry<String, Integer>> ranking = new ArrayList<>(scores.entrySet());
    ranking.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

    Map<String, Integer> result = new LinkedHashMap<>();
    ranking.forEach(entry -> result.put(entry.getKey(), entry.getValue()));

    return result;
  }

  private Map<String, Integer> computeContributorPerProject(List<Contributor> contributors) {
    Map<String, Integer> scores = new HashMap<>();
    contributors.forEach(c -> {
      Integer numberOfContributors = scores.get(c.getProject());
      if (numberOfContributors == null) {
        scores.put(c.getProject(), 1);
      } else {
        scores.put(c.getProject(), numberOfContributors + 1);
      }
    });

    List<Map.Entry<String, Integer>> ranking = new ArrayList<>(scores.entrySet());
    ranking.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

    Map<String, Integer> result = new LinkedHashMap<>();
    ranking.forEach(entry -> result.put(entry.getKey(), entry.getValue()));

    return result;
  }
}
