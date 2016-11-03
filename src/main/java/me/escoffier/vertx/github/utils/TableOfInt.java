package me.escoffier.vertx.github.utils;

import java.util.List;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class TableOfInt extends Table<Integer> {

  public TableOfInt(List<String> columns) {
    super(columns);
  }

  public void inc(String line, String col) {
    Integer val = get(line, col, 0);
    put(line, col, val + 1);
  }

  @Override
  public TableOfInt fill(Integer defaultValue) {
    super.fill(defaultValue);
    return this;
  }
}
