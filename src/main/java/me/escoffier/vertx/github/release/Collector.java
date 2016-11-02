package me.escoffier.vertx.github.release;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class Collector {

  public static List<Issue> retrieveIssues(Project project, String date, Collection<Integer> ignoredIssues) {
    List<Issue> issues = new ArrayList<>();
    OkHttpClient client = new OkHttpClient();
    System.out.println("Retrieving issues for " + project.name());

    int page = 0;
    boolean done = false;

    while (!done) {
      Request request = new Request.Builder()
          .url("https://api.github.com/repos/" + project.id() +
              "/issues?state=closed&since=" + date + "&page=" + page + "&per_page=100&direction=asc&sort=created")
          .addHeader("Authorization", "token " + Token.TOKEN)
          .build();
      System.out.println("\t" + request.url());
      try {
        Response response = client.newCall(request).execute();
        String content = response.body().string();
        JsonArray array = new JsonArray(content);
        System.out.println("\tRetrieved " + array.size() + " issues");
        if (array.isEmpty()) {
          done = true;
          continue;
        } else {
          page++;
        }
        List<Integer> marks = new ArrayList<>();
        for (Object o : array) {
          JsonObject json = (JsonObject) o;

          if (json.getJsonObject("pull_request") != null) {
            String pr = json.getJsonObject("pull_request").getString("url");
            // Check the mergeable state
            String resp = client.newCall(
                new Request.Builder().url(pr)
                    .addHeader("Authorization", "token " + Token.TOKEN)
                    .build()
            ).execute().body().string();

            JsonObject prJSON = new JsonObject(resp);
            if (prJSON.getString("mergeable_state") == null) {
              System.err.println("\tNo mergeable state for " + json.getInteger("number") + " - " + pr);
            } else {
              if (!prJSON.getString("mergeable_state").equalsIgnoreCase("clean")
                  && !prJSON.getString("mergeable_state")
                  .equalsIgnoreCase("unknown")) {
                System.out.println("\tRejecting " + json.getInteger("number") + " PR not in a clean state");
                continue;
              }
            }
          }

          Issue issue = new Issue(json);
          if (issue.isValid() && !ignoredIssues.contains(issue.number) && !alreadyContained(issue, issues)) {
            // Check references
            if (issue.isPR) {
              Set<Integer> references = issue.getReferences();
              System.out.println("\t Issue " + issue.number + " references " + references + " / " + marks);
              boolean referenced = false;
              for (int ref : references) {
                if (marks.contains(ref)) {
                  referenced = true;
                }
              }
              if (!referenced) {
                System.out.println("\tAdding issue " + issue.number);
                issues.add(issue);
                marks.add(issue.number);
              } else {
                System.out.println("\tIgnore " + issue.number + " as it's a PR referencing an issue");
              }
            } else {
              issues.add(issue);
              marks.add(issue.number);
              System.out.println("\tAdding issue " + issue.number);
            }
          } else {
            System.out.println("\tIgnore " + issue.number);
          }
        }
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }


    // Filter out issues that have the same name as a PR
    List<String> actualIssuesNames = issues.stream().filter(i -> !i.isPR).map(i -> i.name).collect(Collectors.toList());
    return issues.stream().filter(i -> !(i.isPR && actualIssuesNames.contains(i.name))).collect(Collectors.toList());
  }

  private static boolean alreadyContained(Issue issue, List<Issue> issues) {
    for (Issue i : issues) {
      if (issue.number == i.number) {
        return true;
      }
    }
    return false;
  }
}
