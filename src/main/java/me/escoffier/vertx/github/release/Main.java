package me.escoffier.vertx.github.release;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.common.base.Charsets;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.io.Files;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Collect release notes.
 */
public class Main {

  @Parameter(names = {"--date", "-d"},
      required = true,
      description = "The last release date such as '2016-07-12T23:00:00Z'")
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

  @Parameter(names = {"--help", "-h"}, help = true, hidden = true)
  private boolean help;

  private static final Logger LOGGER = LogManager.getLogger("Vertx-Release-Notes");

  public static void main(String[] args) {
    Main main = new Main();
    JCommander commander = null;
    try {
      commander = new JCommander(main, args);
    } catch (Exception e) {
      fatal("Unable to parse command line parameters", e);
    }

    if (main.help) {
      commander.usage();
    } else {
      main.execute();
    }
  }

  private void execute() {
    assert repositories.exists();

    Multimap<String, Integer> ignored = parseIgnoredIssues();
    LOGGER.info(ignored.size() + " issues ignored");
    LOGGER.debug("Ignored issues: " + ignored);

    List<Project> projects = Project.load(repositories);
    LOGGER.info(projects.size() + " projects loaded");
    LOGGER.debug("Parsed projects: " + projects.stream().map(Project::id).collect(Collectors.toList()));

    if (output.isFile()) {
      LOGGER.info("The release-notes.md file already exists, deleting it...");
      boolean delete = output.delete();
      LOGGER.debug("File deleted ? " + delete);
    }

    StringBuilder builder = new StringBuilder();
    builder.append("# vert.x ").append(version).append(" - Release Notes \n\n");

    projects.forEach(project -> {
      List<Issue> issues = Collector.retrieveIssues(project, date, ignored.get(project.id()));
      if (!issues.isEmpty()) {
        Report report = new Report(project, issues);
        builder.append(report.toMarkdown());
      }
    });

    LOGGER.info("Writing release notes in " + output.getAbsolutePath());
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

  private static void fatal(String message, Exception cause) {
    LOGGER.error(message, cause);
    System.exit(-1);
  }

}
