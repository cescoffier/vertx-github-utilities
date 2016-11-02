package me.escoffier.vertx.github.model;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class Label {

  private int id;

  private String url;

  private String name;

  public int getId() {
    return id;
  }

  public Label setId(int id) {
    this.id = id;
    return this;
  }

  public String getUrl() {
    return url;
  }

  public Label setUrl(String url) {
    this.url = url;
    return this;
  }

  public String getName() {
    return name;
  }

  public Label setName(String name) {
    this.name = name;
    return this;
  }
}
