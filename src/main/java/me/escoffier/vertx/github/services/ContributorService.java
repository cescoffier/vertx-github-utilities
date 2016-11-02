package me.escoffier.vertx.github.services;

import me.escoffier.vertx.github.model.Contributor;
import me.escoffier.vertx.github.release.Project;
import me.escoffier.vertx.github.utils.GithubClient;
import me.escoffier.vertx.github.utils.Json;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class ContributorService {

  public static Set<Contributor> contributors(Project project) throws IOException {
    System.out.println("Retrieving contributors for " + project.name());
    GithubClient client = new GithubClient();
    List<String> responses = client.request(project.organization(), project.name(), "contributors");

    Set<Contributor> contributors = responses.stream()
        .flatMap(response -> Json.fromJsonAsList(response, Contributor.class).stream())
        .map(contributor -> contributor.setProject(project.id()))
        .distinct()
        .collect(Collectors.toSet());

    System.out.println(project.id() + " => " + contributors.size());
    return contributors;
  }
}
