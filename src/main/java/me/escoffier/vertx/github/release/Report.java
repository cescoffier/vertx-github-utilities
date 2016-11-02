package me.escoffier.vertx.github.release;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class Report {

  public final Project project;
  public final List<Issue> issues = new ArrayList<>();

  public Report(Project project, List<Issue> issues) {
    this.project = project;
    this.issues.addAll(issues);
  }

  public String toMarkdown() {
    StringBuilder builder = new StringBuilder();
    builder.append("## ").append(project.id());
    builder.append("\n\n");
    for (Issue issue : issues) {
      builder.append("* ").append(issue.name).append(" - ").append(issue.url).append("\n");
    }
    builder.append("\n");
    return builder.toString();
  }
}
