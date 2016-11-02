package me.escoffier.vertx.github.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class Stargazer {

  private String date;

  private User user;

  private String project;

  private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");


  @JsonProperty("starred_at")
  public String getDate() {
    return date;
  }

  @JsonProperty("starred_at")
  public Stargazer setDate(String date) {
    this.date = date;
    return this;
  }

  public User getUser() {
    return user;
  }

  public Stargazer setUser(User user) {
    this.user = user;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Stargazer stargazer = (Stargazer) o;

    if (!getUser().equals(stargazer.getUser())) return false;
    return getProject() != null ? getProject().equals(stargazer.getProject()) : stargazer.getProject() == null;
  }

  @Override
  public int hashCode() {
    int result = getUser().hashCode();
    result = 31 * result + (getProject() != null ? getProject().hashCode() : 0);
    return result;
  }

  public Stargazer setProject(String project) {
    this.project = project;
    return this;
  }

  public String getProject() {
    return project;
  }

  public Date toDate() {
    try {
      return format.parse(date);
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }
}
