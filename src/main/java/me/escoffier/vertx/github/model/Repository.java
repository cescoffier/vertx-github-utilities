package me.escoffier.vertx.github.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class Repository {

  private long id;

  private String name;

  @JsonProperty("full_name")
  private String fullName;

  private String description;

  @JsonProperty("stargazers_count")
  private int stars;

  @JsonProperty("open_issues")
  private int openIssues;

  private int forks;

  private String url;

  public long getId() {
    return id;
  }

  public Repository setId(long id) {
    this.id = id;
    return this;
  }

  public String getName() {
    return name;
  }

  public Repository setName(String name) {
    this.name = name;
    return this;
  }

  public String getFullName() {
    return fullName;
  }

  public Repository setFullName(String fullName) {
    this.fullName = fullName;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public Repository setDescription(String description) {
    this.description = description;
    return this;
  }

  public int getStars() {
    return stars;
  }

  public Repository setStars(int stars) {
    this.stars = stars;
    return this;
  }

  public int getOpenIssues() {
    return openIssues;
  }

  public Repository setOpenIssues(int openIssues) {
    this.openIssues = openIssues;
    return this;
  }

  public int getForks() {
    return forks;
  }

  public Repository setForks(int forks) {
    this.forks = forks;
    return this;
  }

  public String getUrl() {
    return url;
  }

  public Repository setUrl(String url) {
    this.url = url;
    return this;
  }

  public String getOrganization() {
    assert fullName != null;
    assert fullName.contains("/");
    return fullName.substring(0, '/');
  }
}
