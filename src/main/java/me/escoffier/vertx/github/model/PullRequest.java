package me.escoffier.vertx.github.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class PullRequest extends Issue {

  private String mergeDate;

  private boolean merged;

  private boolean mergeable;

  private String mergeableState;

  @JsonProperty("merged_at")
  public String getMergeDate() {
    return mergeDate;
  }

  @JsonProperty("merged_at")
  public PullRequest setMergeDate(String mergeDate) {
    this.mergeDate = mergeDate;
    return this;
  }

  public boolean isMerged() {
    return merged;
  }

  public PullRequest setMerged(boolean merged) {
    this.merged = merged;
    return this;
  }

  public boolean isMergeable() {
    return mergeable;
  }

  public PullRequest setMergeable(boolean mergeable) {
    this.mergeable = mergeable;
    return this;
  }

  @JsonProperty("mergeable_state")
  public String getMergeableState() {
    return mergeableState;
  }

  @JsonProperty("mergeable_state")
  public PullRequest setMergeableState(String mergeableState) {
    this.mergeableState = mergeableState;
    return this;
  }
}
