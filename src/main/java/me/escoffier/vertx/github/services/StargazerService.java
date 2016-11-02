package me.escoffier.vertx.github.services;

import me.escoffier.vertx.github.model.Stargazer;
import me.escoffier.vertx.github.release.Project;
import me.escoffier.vertx.github.utils.GithubClient;
import me.escoffier.vertx.github.utils.Json;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class StargazerService {

  public static Set<Stargazer> stargazers(Project project, String token) throws IOException {
    System.out.println("Retrieving stargazers for " + project.name());
    GithubClient client = new GithubClient(token);
    List<String> responses = client.request(project.organization(), project.name(), "stargazers");

    Set<Stargazer> stargazers = responses.stream()
        .flatMap(response -> Json.fromJsonAsList(response, Stargazer.class).stream())
        .map(stargazer -> stargazer.setProject(project.id()))
        .distinct()
        .collect(Collectors.toSet());

    System.out.println(project.id() + " => " + stargazers.size());
    return stargazers;
  }

}
