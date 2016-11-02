package me.escoffier.vertx.github.model;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class User {

  private String login;

  private long id;

  public String getLogin() {
    return login;
  }

  public User setLogin(String login) {
    this.login = login;
    return this;
  }

  public long getId() {
    return id;
  }

  public User setId(long id) {
    this.id = id;
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    User user = (User) o;

    return getLogin().equals(user.getLogin());
  }

  @Override
  public int hashCode() {
    return getLogin().hashCode();
  }
}
