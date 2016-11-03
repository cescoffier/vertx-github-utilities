package me.escoffier.vertx.github.utils;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class TableOfSet<X> extends Table<Set<X>> {

  public TableOfSet(List<String> columns) {
    super(columns);
  }

  public void add(String line, String col, X value) {
    Set<X> set = get(line, col, new LinkedHashSet<>());
    set.add(value);
    put(line, col, set);
  }


}
