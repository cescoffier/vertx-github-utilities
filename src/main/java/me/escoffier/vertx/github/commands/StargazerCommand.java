package me.escoffier.vertx.github.commands;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import me.escoffier.vertx.github.model.Stargazer;
import me.escoffier.vertx.github.release.Project;
import me.escoffier.vertx.github.services.StargazerService;
import me.escoffier.vertx.github.utils.Json;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
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

  @Parameter(names = {"--stargazer-file", "-s"},
      description = "file storing the retrieve stargazerFiles - stargazers.json")
  private File stargazerFiles = new File("stargazers.json");

  @Parameter(names = {"skip-retrieval"},
      description = "whether or not it retrieves the stargazers from Github, if skipped, it uses the 'stargazer-file' as input")
  private boolean skipRetrieval = false;

  @Parameter(names = {"skip-analysis"},
      description = "whether or not it runs the analysis")
  private boolean skipAnalysis = false;

  @Parameter(names = "--star-per-repository", description = "the CSV file in which the number of stars per repository is stored")
  private File starPerRepositoryFile = new File("starPerRepository.csv");

  @Parameter(names = "--star-evolution", description = "the CSV file in which the evolution of the number of stars is stored")
  private File starEvolutionFile = new File("starEvolution.csv");

  public void run() {
    assert repositories.exists();

    List<Project> projects = Project.load(repositories);
    logger.info(projects.size() + " projects loaded");
    logger.debug("Parsed projects: " + projects.stream().map(Project::id).collect(Collectors.toList()));

    List<Stargazer> stargazers;
    if (!skipRetrieval) {
      stargazers = retrieveStargazers(projects);
    } else {
      assert stargazerFiles.exists();
      stargazers = loadStargazers(stargazerFiles);
    }

    if (!skipAnalysis) {
      try {
        // Compute per repo
        computeStarsPerRepo(stargazers);
        // Compute per month
        computeEvolution(stargazers);
      } catch (Exception e) {
        fatal("Unable to analyze stargazers", e);
      }
    }

  }

  private List<Stargazer> retrieveStargazers(List<Project> projects) {
    List<Stargazer> stargazers = new ArrayList<>();
    List<String> users = new ArrayList<>();
    projects.forEach(project -> {
      try {
        Set<Stargazer> result = StargazerService.stargazers(project);
        stargazers.addAll(result);
        users.addAll(result.stream().map(s -> s.getUser().getLogin()).collect(Collectors.toList()));

      } catch (IOException e) {
        fatal("Cannot retrieve the stargazers for " + project.id(), e);
      }
    });

    logger.info("Number of stargazers: " + users.size());
    logger.info("Number of (unique) stargazers: " + new HashSet<>(users).size());

    logger.info("Writing " + stargazers.size() + " objects into " + stargazerFiles.getAbsolutePath());
    if (stargazerFiles.isFile()) {
      stargazerFiles.delete();
    }
    try {
      Files.write(Json.toJson(stargazers), stargazerFiles, Charsets.UTF_8);
    } catch (IOException e) {
      fatal("Unable to write the 'stargazer' files", e);
    }

    return stargazers;
  }

  private List<Stargazer> loadStargazers(File file) {
    Stargazer[] array = Json.fromJson(file, Stargazer[].class);
    return Arrays.asList(array);
  }

  private void computeEvolution(List<Stargazer> stargazers) throws Exception {
    List<Stargazer> sorted = new ArrayList<>(stargazers);
    sorted.sort((s1, s2) -> {
      Date date1 = s1.toDate();
      Date date2 = s2.toDate();
      return date1.compareTo(date2);
    });

    Map<String, Integer> globalStars = new LinkedHashMap<>();
    Map<String, Integer> periodStart = new LinkedHashMap<>();

    int global = 0;
    for (Stargazer stargazer : sorted) {
      global = global + 1;

      Date date = stargazer.toDate();
      Calendar instance = Calendar.getInstance();
      instance.setTime(date);
      String key = (instance.get(Calendar.MONTH) + 1) + "-" + instance.get(Calendar.YEAR);

      globalStars.put(key, global);
      Integer current = periodStart.get(key);
      if (current == null) {
        periodStart.put(key, 1);
      } else {
        periodStart.put(key, current + 1);
      }
    }

    CSVFormat format = CSVFormat.EXCEL.withHeader("date", "month", "total");
    FileWriter writer = new FileWriter(starEvolutionFile);
    CSVPrinter printer = new CSVPrinter(writer, format);

    for (String key : globalStars.keySet()) {
      printer.printRecord(key, periodStart.get(key), globalStars.get(key));
    }

    writer.close();
  }

  private void computeStarsPerRepo(List<Stargazer> stargazers) throws IOException {
    CSVFormat format = CSVFormat.EXCEL.withHeader("repository", "stars");
    FileWriter writer = new FileWriter(starPerRepositoryFile);
    CSVPrinter printer = new CSVPrinter(writer, format);

    Map<String, Integer> starsPerRepo = new TreeMap<>();
    for (Stargazer stargazer : stargazers) {
      Integer count = starsPerRepo.getOrDefault(stargazer.getProject(), 0);
      count = count + 1;
      starsPerRepo.put(stargazer.getProject(), count);
    }

    for (Map.Entry<String, Integer> entry : starsPerRepo.entrySet()) {
      printer.printRecord(entry.getKey(), entry.getValue());
    }

    writer.close();

  }
}
