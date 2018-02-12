package me.escoffier.vertx.github.commands;

import com.beust.jcommander.Parameter;
import com.google.common.base.Charsets;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.io.Files;
import me.escoffier.vertx.github.model.Issue;
import me.escoffier.vertx.github.release.Collector;
import me.escoffier.vertx.github.release.Project;
import me.escoffier.vertx.github.release.Report;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class ReleaseNoteCommand extends AbstractCommand {

  @Parameter(names = {"--date", "-d"},
      required = true,
      description = "The last release date such as '2016-07-12T23:00:00Z' (YYYY-MM-DDTHH:MM:SSZ)")
  private String date;

  @Parameter(names = {"--version", "-v"},
      required = true,
      description = "The Vert.x stack version")
  private String version;

  @Parameter(names = {"--repositories", "-r"},
      required = true,
      description = "The json file listing the repositories")
  private File repositories;

  @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
  @Parameter(names = {"--ignored-issues", "-i"},
      description = "The list of issue for ignore (formatted as 'repo#issue')")
  private List<String> ignored = new ArrayList<>();

  @Parameter(names = {"--output", "-o"},
      description = "output file - release-notes.md by default")
  private File output = new File("release-notes.md");


  @Override
  public void run() {
    assert repositories.exists();

    Multimap<String, Integer> ignored = parseIgnoredIssues();
    logger.info(ignored.size() + " issues ignored");
    logger.debug("Ignored issues: " + ignored);

    List<Project> projects = Project.load(repositories);
    logger.info(projects.size() + " projects loaded");
    logger.debug("Parsed projects: " + projects.stream().map(Project::id).collect(Collectors.toList()));

    if (output.isFile()) {
      logger.info("The release-notes.md file already exists, deleting it...");
      boolean delete = output.delete();
      logger.debug("File deleted ? " + delete);
    }

    StringBuilder builder = new StringBuilder();
    builder.append("# vert.x ").append(version).append(" - Release Notes \n\n");

    projects.forEach(project -> {
      Collection<Issue> issues = Collector.retrieveIssues(project, date, token, ignored.get(project.id()));
      if (!issues.isEmpty()) {
        Report report = new Report(project, issues);
        builder.append(report.toMarkdown());
      }
    });

    logger.info("Writing release notes in " + output.getAbsolutePath());
    try {
      Files.write(builder.toString(), output, Charsets.UTF_8);
    } catch (IOException e) {
      fatal("Unable to write the release note files", e);
    }
  }

  private Multimap<String, Integer> parseIgnoredIssues() {
    Multimap<String, Integer> ignore = ArrayListMultimap.create();
    ignored.forEach(s -> {
      if (!s.contains("#")) {
        fatal("An ignored issue does not follow the format 'repo#issue' : " + s, null);
      }

      String issue = s.substring(s.indexOf("#") + 1);
      String repo = s.substring(0, s.indexOf("#"));

      // Simplify syntax:
      if (!repo.contains("/") && !repo.equals("vert.x")) {
        repo = "vert-x3/" + repo;
      }

      if (!repo.contains("/") && repo.equals("vert.x")) {
        repo = "eclipse/" + repo;
      }

      ignore.put(repo, Integer.parseInt(issue));
    });

    return ignore;
  }

}
