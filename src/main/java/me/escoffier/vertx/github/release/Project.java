package me.escoffier.vertx.github.release;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class Project {

  public static List<Project> load(File file) {
    List<Project> projects = new ArrayList<>();
    try {
      ArrayNode all = (ArrayNode) new ObjectMapper().readTree(file);
      for (JsonNode node : all) {
        String name = node.get("repo").asText();
        String org = "vert-x3";
        if (node.get("org") != null) {
          org = node.get("org").asText();
        }
        Objects.requireNonNull(name);
        projects.add(new Project(name, org));
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("Cannot read the projects from " + file.getAbsolutePath(), e);
    }

    // Uniqueness check
    Set<String> names = new HashSet<>();
    for (Project project : projects) {
      if (! names.add(project.name)) {
        throw new RuntimeException("Project " + project.name + " duplicated");
      }
    }

    return projects;
  }


  private final String name;

  private final String organization;

  public Project(String name, String organization) {
    this.name = name;
    this.organization = organization;
  }

  public String name() {
    return name;
  }

  public String organization() {
    return organization;
  }


  public String url() {
    return "https://github.com/" + id();
  }

  public String id() {
    return organization + "/" + name;
  }
}
