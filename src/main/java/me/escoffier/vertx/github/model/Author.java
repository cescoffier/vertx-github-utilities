package me.escoffier.vertx.github.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class Author {

  private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");

  private String name;

  private String email;

  private String date;

  public String getName() {
    return name;
  }

  public Author setName(String name) {
    this.name = name;
    return this;
  }

  public String getEmail() {
    return email;
  }

  public Author setEmail(String email) {
    this.email = email;
    return this;
  }

  public String getDate() {
    return date;
  }

  public Author setDate(String date) {
    this.date = date;
    return this;
  }

  public Date toDate() {
    try {
      return format.parse(date);
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
  }
}
