package me.escoffier.vertx.github.release;

import me.escoffier.vertx.github.model.Issue;
import me.escoffier.vertx.github.model.PullRequest;
import me.escoffier.vertx.github.services.IssueService;
import me.escoffier.vertx.github.services.PullRequestService;

import java.io.IOException;
import java.util.*;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class Collector {

  // TODO Outdated class, need to be redone....

  private static boolean alreadyContained(Issue issue, Collection<Issue> issues) {
    for (Issue i : issues) {
      if (issue.getNumber() == i.getNumber()) {
        return true;
      }
    }
    return false;
  }

  private static boolean alreadyContained(int issue, Collection<Issue> issues) {
    for (Issue i : issues) {
      if (issue == i.getNumber()) {
        return true;
      }
    }
    return false;
  }


  public static Collection<Issue> retrieveIssues(Project project, String date, String token,
                                                 Collection<Integer> ignoredIssues) {
    System.out.println("Retrieving issues for " + project.name());
    try {
      Set<Issue> issues = IssueService.issues(project, token, new IssueService.Query().since(date).state("closed")
          .ascending().sort("created"));

      Map<Integer, PullRequest> pullRequests = new HashMap<>();
      issues.stream().filter(Issue::isPR)
          .map(issue -> issue.getPr().getUrl())
          .map(url -> PullRequestService.retrieve(url, token))
          .forEach(pr -> pullRequests.put(pr.getNumber(), pr));

      Set<Issue> consolidatedListOfIssues = new LinkedHashSet<>();
      issues.forEach(issue -> {
        PullRequest pr = pullRequests.get(issue.getNumber());
        if (pr == null) {
          if (issue.shouldBePartOfReleaseNotes() && !ignoredIssues.contains(issue.getNumber())
              && !alreadyContained(issue, consolidatedListOfIssues)) {
            consolidatedListOfIssues.add(issue);
          }
        } else if (pr.isMerged() || "clean".equalsIgnoreCase(pr.getMergeableState())
            && !alreadyContained(issue, consolidatedListOfIssues)) {

          // Check whether it reference another issue
          Set<Integer> references = pr.getReferences();
          // TODO check whether or not an issue has a reference.


          if (issue.shouldBePartOfReleaseNotes() && !ignoredIssues.contains(issue.getNumber())) {
            consolidatedListOfIssues.add(issue);
          }
        } else {
          System.out.println("Rejecting pull request " + issue.getNumber() + " - not merged or not in a meargeable " +
              "state: " + pr.getMergeableState());
        }
      });

      return consolidatedListOfIssues;

    } catch (IOException e) {
      throw new RuntimeException("Unable to retrieve issues for " + project.id(), e);
    }
  }
}

