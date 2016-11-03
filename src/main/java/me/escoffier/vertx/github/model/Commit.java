package me.escoffier.vertx.github.model;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class Commit {

  private Author author;

  private Author committer;

  private String message;

  public Author getAuthor() {
    return author;
  }

  public Commit setAuthor(Author author) {
    this.author = author;
    return this;
  }

  public Author getCommitter() {
    return committer;
  }

  public Commit setCommitter(Author committer) {
    this.committer = committer;
    return this;
  }

  public String getMessage() {
    return message;
  }

  public Commit setMessage(String message) {
    this.message = message;
    return this;
  }
}
