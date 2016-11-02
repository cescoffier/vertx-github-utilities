package me.escoffier.vertx.github.services;

import me.escoffier.vertx.github.model.Repository;
import me.escoffier.vertx.github.release.Project;
import me.escoffier.vertx.github.utils.GithubClient;

import java.io.IOException;

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

}
