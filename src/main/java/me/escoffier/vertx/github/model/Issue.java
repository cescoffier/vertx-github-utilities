package me.escoffier.vertx.github.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
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

  private String project;

  private int id;

  private String url;

  private String link;

  private String title;

  private int number;

  private String state;

  private String body;

  private User user;

  private List<Label> labels = new ArrayList<>();

  private User assignee;

  private PullRequest pr;

  private String closedDate;

  private String creationDate;

  private String updatedDate;

  private User closedBy;

  public String getProject() {
    return project;
  }

  public Issue setProject(String project) {
    this.project = project;
    return this;
  }

  public int getId() {
    return id;
  }

  public Issue setId(int id) {
    this.id = id;
    return this;
  }

  @JsonProperty("html_url")
  public String getLink() {
    return link;
  }

  @JsonProperty("html_url")
  public Issue setLink(String link) {
    this.link = link;
    return this;
  }

  public String getUrl() {
    return url;
  }

  public Issue setUrl(String url) {
    this.url = url;
    return this;
  }

  public String getTitle() {
    return title;
  }

  public Issue setTitle(String title) {
    this.title = title;
    return this;
  }

  public int getNumber() {
    return number;
  }

  public Issue setNumber(int number) {
    this.number = number;
    return this;
  }

  public String getState() {
    return state;
  }

  public Issue setState(String state) {
    this.state = state;
    return this;
  }

  public String getBody() {
    return body;
  }

  public Issue setBody(String body) {
    this.body = body;
    return this;
  }

  public User getUser() {
    return user;
  }

  public Issue setUser(User user) {
    this.user = user;
    return this;
  }

  public List<Label> getLabels() {
    return labels;
  }

  public Issue setLabels(List<Label> labels) {
    this.labels = labels;
    return this;
  }

  public User getAssignee() {
    return assignee;
  }

  public Issue setAssignee(User assignee) {
    this.assignee = assignee;
    return this;
  }

  @JsonProperty("pull_request")
  public PullRequest getPr() {
    return pr;
  }

  @JsonProperty("pull_request")
  public Issue setPr(PullRequest pr) {
    this.pr = pr;
    return this;
  }

  public boolean isPR() {
    return pr != null;
  }

  @JsonProperty("closed_at")
  public String getClosedDate() {
    return closedDate;
  }

  @JsonProperty("closed_at")
  public Issue setClosedDate(String closedDate) {
    this.closedDate = closedDate;
    return this;
  }

  @JsonProperty("created_at")
  public String getCreationDate() {
    return creationDate;
  }

  @JsonProperty("created_at")
  public Issue setCreationDate(String creationDate) {
    this.creationDate = creationDate;
    return this;
  }

  @JsonProperty("updated_at")
  public String getUpdatedDate() {
    return updatedDate;
  }

  @JsonProperty("updated_at")
  public Issue setUpdatedDate(String updatedDate) {
    this.updatedDate = updatedDate;
    return this;
  }

  public User getClosedBy() {
    return closedBy;
  }

  public Issue setClosedBy(User closedBy) {
    this.closedBy = closedBy;
    return this;
  }

  public String getFormattedTitle() {
    String title = getTitle();
    if (title.endsWith("…")) {
      String body = getBody();

      int firstDot = body.indexOf('.');
      int firstLine = body.indexOf('\n');

      int cut;
      if (firstDot == -1  && firstLine == -1) {
        cut = -1;
      } else if (firstDot == -1) {
        cut = firstLine;
      } else if (firstLine == -1) {
        cut = firstDot;
      } else {
        cut = firstDot > firstLine ? firstLine : firstDot;
      }

      if (cut != -1) {
        title = title.substring(0, title.length() - 1) + body.substring(1, cut);
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

    Matcher matcher = pattern.matcher(getTitle());
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

  public boolean shouldBePartOfReleaseNotes() {
    Set<String> labels = getLabels().stream().map(Label::getName).collect(Collectors.toSet());
    return ! labels.contains("invalid")
        && ! labels.contains("wontfix")
        && ! labels.contains("duplicate");
  }
}
