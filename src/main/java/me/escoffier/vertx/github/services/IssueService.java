package me.escoffier.vertx.github.services;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import me.escoffier.vertx.github.model.Issue;
import me.escoffier.vertx.github.release.Project;
import me.escoffier.vertx.github.utils.GithubClient;
import me.escoffier.vertx.github.utils.Json;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class IssueService {

  public static class Query {
    String state = "open";
    String dir = "desc";
    String sort = "created";
    String since;

    private static final List<String> STATES = ImmutableList.of("open", "closed", "all");
    private static final List<String> CRITERIA = ImmutableList.of("created", "updated", "comments");


    public Query state(String s) {
      assert s != null;
      state = s.toLowerCase();
      if (!STATES.contains(s)) {
        throw new IllegalArgumentException("Unsupported state " + s);
      }
      return this;
    }

    public Query ascending() {
      dir = "asc";
      return this;
    }

    public Query descending() {
      dir = "desc";
      return this;
    }

    public Query sort(String criteria) {
      assert criteria != null;
      sort = criteria.toLowerCase();
      if (!CRITERIA.contains(sort)) {
        throw new IllegalArgumentException("Unsupported sort criteria " + criteria);
      }
      return this;
    }

    public Query since(String date) {
      assert date != null;
      since = date;
      return this;
    }

    Map<String, String> build() {
      ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
      builder
          .put("state", state)
          .put("direction", dir)
          .put("sort", sort);
      if (since != null) {
        builder.put("since", since);
      }
      return builder.build();
    }

  }

  public static Set<Issue> issues(Project project, String token) throws IOException {
    return issues(project, token, null);
  }

  public static Set<Issue> issues(Project project, String token, Query query) throws IOException {
    if (query == null) {
      query = new Query();
    }
    GithubClient client = new GithubClient(token);
    List<String> responses = client.request(project.organization(), project.name(), "issues", query.build());

    return responses.stream()
        .flatMap(response -> Json.fromJsonAsList(response, Issue.class).stream())
        .map(issue -> issue.setProject(project.id()))
        .distinct()
        .collect(Collectors.toSet());
  }

}
