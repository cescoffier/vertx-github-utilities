package me.escoffier.vertx.github.utils;

import com.google.common.collect.Iterables;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class Table<X> {

  private final List<String> columns;
  private Map<String, Map<String, X>> table = new LinkedHashMap<>();

  public Table(List<String> columns) {
    this.columns = new ArrayList<>(columns);
  }

  public Table<X> put(String line, String col, X value) {
    if (!columns.contains(col)) {
      throw new IllegalArgumentException("Unknown column " + col);
    }
    Map<String, X> map = table.computeIfAbsent(line, k -> new LinkedHashMap<>());
    map.put(col, value);
    return this;
  }

  public X get(String line, String col) {
    if (!columns.contains(col)) {
      throw new IllegalArgumentException("Unknown column " + col);
    }
    Map<String, X> map = table.get(line);
    if (map == null) {
      return null;
    } else {
      return map.get(col);
    }
  }

  public X get(String line, String col, X defaultValue) {
    if (!columns.contains(col)) {
      throw new IllegalArgumentException("Unknown column " + col);
    }
    Map<String, X> map = table.get(line);
    if (map == null) {
      return defaultValue;
    } else {
      X val = map.get(col);
      if (val != null) {
        return val;
      } else {
        return defaultValue;
      }
    }
  }

  public Map<String, X> getLine(int index) {
    String s = Iterables.get(table.keySet(), index);
    return getLine(s);
  }

  public List<X> getValues(int index) {
    List<X> result = new ArrayList<>();
    Map<String, X> line = getLine(index);
    for (String col : columns) {
      result.add(line.get(col));
    }
    return result;
  }

  public Map<String, X> getLine(String name) {
    Map<String, X> result = new LinkedHashMap<>();
    Map<String, X> line = table.get(name);
    for (String col : columns) {
      result.put(col, line.get(col));
    }
    return result;
  }

  public Map<String, Map<String, X>> getTable() {
    return table;
  }

  public int size() {
    return table.size();
  }

  public Table<X> fill(X defaultValue) {
    table.values().forEach(data -> getDataColumns().forEach(col -> data.putIfAbsent(col, defaultValue)));
    return this;
  }

  public List<String> getColumns() {
    return columns;
  }

  public List<String> getDataColumns() {
    return columns.subList(1, columns.size());
  }

  public void toCSV(File file) throws IOException {
    CSVUtils.write(file, this);
  }
}
