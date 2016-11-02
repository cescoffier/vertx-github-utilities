package me.escoffier.vertx.github.model;

import me.escoffier.vertx.github.utils.Json;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class IssueTest {


   String response = "[\n" +
       "  {\n" +
       "    \"id\": 1,\n" +
       "    \"url\": \"https://api.github.com/repos/octocat/Hello-World/issues/1347\",\n" +
       "    \"repository_url\": \"https://api.github.com/repos/octocat/Hello-World\",\n" +
       "    \"labels_url\": \"https://api.github.com/repos/octocat/Hello-World/issues/1347/labels{/name}\",\n" +
       "    \"comments_url\": \"https://api.github.com/repos/octocat/Hello-World/issues/1347/comments\",\n" +
       "    \"events_url\": \"https://api.github.com/repos/octocat/Hello-World/issues/1347/events\",\n" +
       "    \"html_url\": \"https://github.com/octocat/Hello-World/issues/1347\",\n" +
       "    \"number\": 1347,\n" +
       "    \"state\": \"open\",\n" +
       "    \"title\": \"Found a bug\",\n" +
       "    \"body\": \"I'm having a problem with this.\",\n" +
       "    \"user\": {\n" +
       "      \"login\": \"octocat\",\n" +
       "      \"id\": 1,\n" +
       "      \"avatar_url\": \"https://github.com/images/error/octocat_happy.gif\",\n" +
       "      \"gravatar_id\": \"\",\n" +
       "      \"url\": \"https://api.github.com/users/octocat\",\n" +
       "      \"html_url\": \"https://github.com/octocat\",\n" +
       "      \"followers_url\": \"https://api.github.com/users/octocat/followers\",\n" +
       "      \"following_url\": \"https://api.github.com/users/octocat/following{/other_user}\",\n" +
       "      \"gists_url\": \"https://api.github.com/users/octocat/gists{/gist_id}\",\n" +
       "      \"starred_url\": \"https://api.github.com/users/octocat/starred{/owner}{/repo}\",\n" +
       "      \"subscriptions_url\": \"https://api.github.com/users/octocat/subscriptions\",\n" +
       "      \"organizations_url\": \"https://api.github.com/users/octocat/orgs\",\n" +
       "      \"repos_url\": \"https://api.github.com/users/octocat/repos\",\n" +
       "      \"events_url\": \"https://api.github.com/users/octocat/events{/privacy}\",\n" +
       "      \"received_events_url\": \"https://api.github.com/users/octocat/received_events\",\n" +
       "      \"type\": \"User\",\n" +
       "      \"site_admin\": false\n" +
       "    },\n" +
       "    \"labels\": [\n" +
       "      {\n" +
       "        \"id\": 208045946,\n" +
       "        \"url\": \"https://api.github.com/repos/octocat/Hello-World/labels/bug\",\n" +
       "        \"name\": \"bug\",\n" +
       "        \"color\": \"f29513\",\n" +
       "        \"default\": true\n" +
       "      }\n" +
       "    ],\n" +
       "    \"assignee\": {\n" +
       "      \"login\": \"octocat\",\n" +
       "      \"id\": 1,\n" +
       "      \"avatar_url\": \"https://github.com/images/error/octocat_happy.gif\",\n" +
       "      \"gravatar_id\": \"\",\n" +
       "      \"url\": \"https://api.github.com/users/octocat\",\n" +
       "      \"html_url\": \"https://github.com/octocat\",\n" +
       "      \"followers_url\": \"https://api.github.com/users/octocat/followers\",\n" +
       "      \"following_url\": \"https://api.github.com/users/octocat/following{/other_user}\",\n" +
       "      \"gists_url\": \"https://api.github.com/users/octocat/gists{/gist_id}\",\n" +
       "      \"starred_url\": \"https://api.github.com/users/octocat/starred{/owner}{/repo}\",\n" +
       "      \"subscriptions_url\": \"https://api.github.com/users/octocat/subscriptions\",\n" +
       "      \"organizations_url\": \"https://api.github.com/users/octocat/orgs\",\n" +
       "      \"repos_url\": \"https://api.github.com/users/octocat/repos\",\n" +
       "      \"events_url\": \"https://api.github.com/users/octocat/events{/privacy}\",\n" +
       "      \"received_events_url\": \"https://api.github.com/users/octocat/received_events\",\n" +
       "      \"type\": \"User\",\n" +
       "      \"site_admin\": false\n" +
       "    },\n" +
       "    \"milestone\": {\n" +
       "      \"url\": \"https://api.github.com/repos/octocat/Hello-World/milestones/1\",\n" +
       "      \"html_url\": \"https://github.com/octocat/Hello-World/milestones/v1.0\",\n" +
       "      \"labels_url\": \"https://api.github.com/repos/octocat/Hello-World/milestones/1/labels\",\n" +
       "      \"id\": 1002604,\n" +
       "      \"number\": 1,\n" +
       "      \"state\": \"open\",\n" +
       "      \"title\": \"v1.0\",\n" +
       "      \"description\": \"Tracking milestone for version 1.0\",\n" +
       "      \"creator\": {\n" +
       "        \"login\": \"octocat\",\n" +
       "        \"id\": 1,\n" +
       "        \"avatar_url\": \"https://github.com/images/error/octocat_happy.gif\",\n" +
       "        \"gravatar_id\": \"\",\n" +
       "        \"url\": \"https://api.github.com/users/octocat\",\n" +
       "        \"html_url\": \"https://github.com/octocat\",\n" +
       "        \"followers_url\": \"https://api.github.com/users/octocat/followers\",\n" +
       "        \"following_url\": \"https://api.github.com/users/octocat/following{/other_user}\",\n" +
       "        \"gists_url\": \"https://api.github.com/users/octocat/gists{/gist_id}\",\n" +
       "        \"starred_url\": \"https://api.github.com/users/octocat/starred{/owner}{/repo}\",\n" +
       "        \"subscriptions_url\": \"https://api.github.com/users/octocat/subscriptions\",\n" +
       "        \"organizations_url\": \"https://api.github.com/users/octocat/orgs\",\n" +
       "        \"repos_url\": \"https://api.github.com/users/octocat/repos\",\n" +
       "        \"events_url\": \"https://api.github.com/users/octocat/events{/privacy}\",\n" +
       "        \"received_events_url\": \"https://api.github.com/users/octocat/received_events\",\n" +
       "        \"type\": \"User\",\n" +
       "        \"site_admin\": false\n" +
       "      },\n" +
       "      \"open_issues\": 4,\n" +
       "      \"closed_issues\": 8,\n" +
       "      \"created_at\": \"2011-04-10T20:09:31Z\",\n" +
       "      \"updated_at\": \"2014-03-03T18:58:10Z\",\n" +
       "      \"closed_at\": \"2013-02-12T13:22:01Z\",\n" +
       "      \"due_on\": \"2012-10-09T23:39:01Z\"\n" +
       "    },\n" +
       "    \"locked\": false,\n" +
       "    \"comments\": 0,\n" +
       "    \"pull_request\": {\n" +
       "      \"url\": \"https://api.github.com/repos/octocat/Hello-World/pulls/1347\",\n" +
       "      \"html_url\": \"https://github.com/octocat/Hello-World/pull/1347\",\n" +
       "      \"diff_url\": \"https://github.com/octocat/Hello-World/pull/1347.diff\",\n" +
       "      \"patch_url\": \"https://github.com/octocat/Hello-World/pull/1347.patch\"\n" +
       "    },\n" +
       "    \"closed_at\": null,\n" +
       "    \"created_at\": \"2011-04-22T13:33:48Z\",\n" +
       "    \"updated_at\": \"2011-04-22T13:33:48Z\"\n" +
       "  }\n" +
       "]";


   @Test
   public void test() {
     List<Issue> list = Json.fromJsonAsList(response, Issue.class);
     assertThat(list).hasSize(1);

     Issue issue = list.get(0);
     assertThat(issue.isPR()).isTrue();
     assertThat(issue.getClosedBy()).isNull();
     assertThat(issue.getCreationDate()).isNotNull();
     assertThat(issue.getUpdatedDate()).isNotNull();

     assertThat(issue.getTitle()).isNotNull();
     assertThat(issue.getBody()).isNotNull();

     assertThat(issue.getId()).isNotZero();
     assertThat(issue.getNumber()).isNotZero();

     assertThat(issue.getUser().getLogin()).isEqualTo("octocat");

     assertThat(issue.getState()).isEqualTo("open");

     assertThat(issue.getLabels()).hasSize(1);
   }

}