package me.escoffier.vertx.github.services;

import me.escoffier.vertx.github.model.PullRequest;
import me.escoffier.vertx.github.utils.GithubClient;

import java.io.IOException;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class PullRequestService {

  public static PullRequest retrieve(String url, String token) {
    GithubClient client = new GithubClient(token);
    try {
      return client.request(url, PullRequest.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }

}
