package me.escoffier.vertx.github.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class Issue {

  private String project;

  private int id;

  private String url;

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
}
