package me.escoffier.vertx.github.commands;

import com.beust.jcommander.Parameter;
import com.google.common.collect.ImmutableList;
import me.escoffier.vertx.github.model.Repository;
import me.escoffier.vertx.github.release.Project;
import me.escoffier.vertx.github.services.IssueService;
import me.escoffier.vertx.github.services.RepositoryService;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class IssueCommand extends AbstractCommand {

  @Parameter(names = {"--repositories", "-r"},
      required = true,
      description = "The json file listing the repositories")
  private File repositories;

  @Parameter(names = "output", description = "output file (csv)")
  private File output = new File("issues.csv");

  @Override
  public void run() {
    List<Project> projects = Project.load(repositories);
    logger.info(projects.size() + " projects loaded");
    logger.debug("Parsed projects: " + projects.stream().map(Project::id).collect(Collectors.toList()));


    List<Repository> repositories = projects.stream()
        .map(project -> RepositoryService.retrieve(project, token))
        .collect(Collectors.toList());

    int total = repositories.stream().mapToInt(Repository::getOpenIssues).sum();
    logger.info("Total number of (open) issues: " + total);

    List<List<String>> list = repositories.stream()
        .map(r -> ImmutableList.of(r.getFullName(), Integer.toString(r.getOpenIssues())))
        .collect(Collectors.toList());

    try {
      CSVUtils.write(output, ImmutableList.of("repository", "number of open issues"), list);
    } catch (IOException e) {
      fatal("Unable to write file " + output.getAbsolutePath(), e);
    }

  }
}
