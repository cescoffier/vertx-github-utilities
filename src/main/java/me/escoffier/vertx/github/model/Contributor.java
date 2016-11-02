package me.escoffier.vertx.github.model;

import me.escoffier.vertx.github.release.Project;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class Contributor extends User {

  private int contributions;
  private String project;

  public int getContributions() {
    return contributions;
  }

  public Contributor setContributions(int contributions) {
    this.contributions = contributions;
    return this;
  }

  public Contributor setProject(String project) {
    this.project = project;
    return this;
  }

  public String getProject() {
    return project;
  }
}
