package me.escoffier.vertx.github.release;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class Issue {

  public final String name;

  public final int number;

  public final String url;

  public final List<String> labels;

  public final boolean isPR;

  private final String body;

  public Issue(String name, int number, String url, List<String> labels, String body, boolean isPR) {
    this.name = name;
    this.number = number;
    this.url = url;
    this.labels = labels;
    this.isPR = isPR;
    this.body = body;
  }

  public Issue(JsonObject json) {
    this(
        computeTitle(json),
        json.getInteger("number"),
        json.getString("html_url"),
        json.getJsonArray("labels", new JsonArray(Collections.emptyList())).stream().map(o -> ((JsonObject) o)
            .getString("name").toLowerCase()).collect(Collectors.toList()),
        json.getString("body", ""),
        json.getJsonObject("pull_request") != null
    );
  }

  private static String computeTitle(JsonObject json) {
    String title = json.getString("title");
    if (title.endsWith("…")) {
      String body = json.getString("body");
      if (body.indexOf('.') != -1) {
        title = title.substring(0, title.length() - 1) + body.substring(1, body.indexOf('.'));
      } else {
        title = title.substring(0, title.length() - 1) + body.substring(1);
      }
    }
    return title;
  }

  /**
   * Find references issues, if any.
   *
   * @return the list of references
   */
  public Set<Integer> getReferences() {
    Pattern pattern = Pattern.compile("#([0-9]+)");
    Set<Integer> references = new LinkedHashSet<>();

    Matcher matcher = pattern.matcher(name);
    while (matcher.find()) {
      references.add(Integer.parseInt(matcher.group(1)));
    }

    if (body != null) {
      matcher = pattern.matcher(body);
      while (matcher.find()) {
        references.add(Integer.parseInt(matcher.group(1)));
      }
    }

    return references;
  }

  public boolean isValid() {
    return ! labels.contains("invalid")
        && ! labels.contains("wontfix")
        && ! labels.contains("duplicate");
  }
}
