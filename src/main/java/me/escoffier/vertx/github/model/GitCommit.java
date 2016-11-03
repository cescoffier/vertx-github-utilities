package me.escoffier.vertx.github.model;

import java.util.Date;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class GitCommit {

  private String project;

  private String sha;

  private User author;

  private User committer;

  private Commit commit;

  public String getSha() {
    return sha;
  }

  public GitCommit setSha(String sha) {
    this.sha = sha;
    return this;
  }

  public User getAuthor() {
    return author;
  }

  public GitCommit setAuthor(User author) {
    this.author = author;
    return this;
  }

  public User getCommitter() {
    return committer;
  }

  public GitCommit setCommitter(User committer) {
    this.committer = committer;
    return this;
  }

  public Commit getCommit() {
    return commit;
  }

  public GitCommit setCommit(Commit commit) {
    this.commit = commit;
    return this;
  }

  public String getProject() {
    return project;
  }

  public GitCommit setProject(String project) {
    this.project = project;
    return this;
  }

  public Date toDate() {
    return commit.getAuthor().toDate();
  }

  public String getContributor() {
    return commit.getAuthor().getName();
  }
}
