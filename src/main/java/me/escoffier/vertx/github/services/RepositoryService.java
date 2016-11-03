package me.escoffier.vertx.github.services;

import me.escoffier.vertx.github.model.Commit;
import me.escoffier.vertx.github.model.GitCommit;
import me.escoffier.vertx.github.model.Repository;
import me.escoffier.vertx.github.model.Stargazer;
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
public class RepositoryService {

  public static Repository retrieve(Project project, String token) {
    GithubClient client = new GithubClient(token);
    String url = GithubClient.ROOT + "/" + project.id();
    try {
      return client.request(url, Repository.class);
    } catch (IOException e) {
      throw new RuntimeException("Unable to retrieve repository " + project.id(), e);
    }
  }

  public static Set<GitCommit> log(Project project, String token) throws IOException {
    GithubClient client = new GithubClient(token);
    List<String> responses = client.request(project.organization(), project.name(), "commits");

    return responses.stream()
        .flatMap(response -> Json.fromJsonAsList(response, GitCommit.class).stream())
        .map(commit -> commit.setProject(project.id()))
        .collect(Collectors.toSet());
  }

}
