package me.escoffier.vertx.github.model;

import me.escoffier.vertx.github.utils.Json;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class RepositoryTest {

  String response = "[\n" +
      "  {\n" +
      "    \"id\": 20025149,\n" +
      "    \"name\": \"vertx-codegen\",\n" +
      "    \"full_name\": \"vert-x3/vertx-codegen\",\n" +
      "    \"owner\": {\n" +
      "      \"login\": \"vert-x3\",\n" +
      "      \"id\": 8124623,\n" +
      "      \"avatar_url\": \"https://avatars.githubusercontent.com/u/8124623?v=3\",\n" +
      "      \"gravatar_id\": \"\",\n" +
      "      \"url\": \"https://api.github.com/users/vert-x3\",\n" +
      "      \"html_url\": \"https://github.com/vert-x3\",\n" +
      "      \"followers_url\": \"https://api.github.com/users/vert-x3/followers\",\n" +
      "      \"following_url\": \"https://api.github.com/users/vert-x3/following{/other_user}\",\n" +
      "      \"gists_url\": \"https://api.github.com/users/vert-x3/gists{/gist_id}\",\n" +
      "      \"starred_url\": \"https://api.github.com/users/vert-x3/starred{/owner}{/repo}\",\n" +
      "      \"subscriptions_url\": \"https://api.github.com/users/vert-x3/subscriptions\",\n" +
      "      \"organizations_url\": \"https://api.github.com/users/vert-x3/orgs\",\n" +
      "      \"repos_url\": \"https://api.github.com/users/vert-x3/repos\",\n" +
      "      \"events_url\": \"https://api.github.com/users/vert-x3/events{/privacy}\",\n" +
      "      \"received_events_url\": \"https://api.github.com/users/vert-x3/received_events\",\n" +
      "      \"type\": \"Organization\",\n" +
      "      \"site_admin\": false\n" +
      "    },\n" +
      "    \"private\": false,\n" +
      "    \"html_url\": \"https://github.com/vert-x3/vertx-codegen\",\n" +
      "    \"description\": null,\n" +
      "    \"fork\": false,\n" +
      "    \"url\": \"https://api.github.com/repos/vert-x3/vertx-codegen\",\n" +
      "    \"forks_url\": \"https://api.github.com/repos/vert-x3/vertx-codegen/forks\",\n" +
      "    \"keys_url\": \"https://api.github.com/repos/vert-x3/vertx-codegen/keys{/key_id}\",\n" +
      "    \"collaborators_url\": \"https://api.github.com/repos/vert-x3/vertx-codegen/collaborators{/collaborator}\",\n" +
      "    \"teams_url\": \"https://api.github.com/repos/vert-x3/vertx-codegen/teams\",\n" +
      "    \"hooks_url\": \"https://api.github.com/repos/vert-x3/vertx-codegen/hooks\",\n" +
      "    \"issue_events_url\": \"https://api.github.com/repos/vert-x3/vertx-codegen/issues/events{/number}\",\n" +
      "    \"events_url\": \"https://api.github.com/repos/vert-x3/vertx-codegen/events\",\n" +
      "    \"assignees_url\": \"https://api.github.com/repos/vert-x3/vertx-codegen/assignees{/user}\",\n" +
      "    \"branches_url\": \"https://api.github.com/repos/vert-x3/vertx-codegen/branches{/branch}\",\n" +
      "    \"tags_url\": \"https://api.github.com/repos/vert-x3/vertx-codegen/tags\",\n" +
      "    \"blobs_url\": \"https://api.github.com/repos/vert-x3/vertx-codegen/git/blobs{/sha}\",\n" +
      "    \"git_tags_url\": \"https://api.github.com/repos/vert-x3/vertx-codegen/git/tags{/sha}\",\n" +
      "    \"git_refs_url\": \"https://api.github.com/repos/vert-x3/vertx-codegen/git/refs{/sha}\",\n" +
      "    \"trees_url\": \"https://api.github.com/repos/vert-x3/vertx-codegen/git/trees{/sha}\",\n" +
      "    \"statuses_url\": \"https://api.github.com/repos/vert-x3/vertx-codegen/statuses/{sha}\",\n" +
      "    \"languages_url\": \"https://api.github.com/repos/vert-x3/vertx-codegen/languages\",\n" +
      "    \"stargazers_url\": \"https://api.github.com/repos/vert-x3/vertx-codegen/stargazers\",\n" +
      "    \"contributors_url\": \"https://api.github.com/repos/vert-x3/vertx-codegen/contributors\",\n" +
      "    \"subscribers_url\": \"https://api.github.com/repos/vert-x3/vertx-codegen/subscribers\",\n" +
      "    \"subscription_url\": \"https://api.github.com/repos/vert-x3/vertx-codegen/subscription\",\n" +
      "    \"commits_url\": \"https://api.github.com/repos/vert-x3/vertx-codegen/commits{/sha}\",\n" +
      "    \"git_commits_url\": \"https://api.github.com/repos/vert-x3/vertx-codegen/git/commits{/sha}\",\n" +
      "    \"comments_url\": \"https://api.github.com/repos/vert-x3/vertx-codegen/comments{/number}\",\n" +
      "    \"issue_comment_url\": \"https://api.github.com/repos/vert-x3/vertx-codegen/issues/comments{/number}\",\n" +
      "    \"contents_url\": \"https://api.github.com/repos/vert-x3/vertx-codegen/contents/{+path}\",\n" +
      "    \"compare_url\": \"https://api.github.com/repos/vert-x3/vertx-codegen/compare/{base}...{head}\",\n" +
      "    \"merges_url\": \"https://api.github.com/repos/vert-x3/vertx-codegen/merges\",\n" +
      "    \"archive_url\": \"https://api.github.com/repos/vert-x3/vertx-codegen/{archive_format}{/ref}\",\n" +
      "    \"downloads_url\": \"https://api.github.com/repos/vert-x3/vertx-codegen/downloads\",\n" +
      "    \"issues_url\": \"https://api.github.com/repos/vert-x3/vertx-codegen/issues{/number}\",\n" +
      "    \"pulls_url\": \"https://api.github.com/repos/vert-x3/vertx-codegen/pulls{/number}\",\n" +
      "    \"milestones_url\": \"https://api.github.com/repos/vert-x3/vertx-codegen/milestones{/number}\",\n" +
      "    \"notifications_url\": \"https://api.github.com/repos/vert-x3/vertx-codegen/notifications{?since,all,participating}\",\n" +
      "    \"labels_url\": \"https://api.github.com/repos/vert-x3/vertx-codegen/labels{/name}\",\n" +
      "    \"releases_url\": \"https://api.github.com/repos/vert-x3/vertx-codegen/releases{/id}\",\n" +
      "    \"deployments_url\": \"https://api.github.com/repos/vert-x3/vertx-codegen/deployments\",\n" +
      "    \"created_at\": \"2014-05-21T14:53:16Z\",\n" +
      "    \"updated_at\": \"2016-10-28T06:49:55Z\",\n" +
      "    \"pushed_at\": \"2016-10-31T22:54:51Z\",\n" +
      "    \"git_url\": \"git://github.com/vert-x3/vertx-codegen.git\",\n" +
      "    \"ssh_url\": \"git@github.com:vert-x3/vertx-codegen.git\",\n" +
      "    \"clone_url\": \"https://github.com/vert-x3/vertx-codegen.git\",\n" +
      "    \"svn_url\": \"https://github.com/vert-x3/vertx-codegen\",\n" +
      "    \"homepage\": null,\n" +
      "    \"size\": 2517,\n" +
      "    \"stargazers_count\": 22,\n" +
      "    \"watchers_count\": 22,\n" +
      "    \"language\": \"Java\",\n" +
      "    \"has_issues\": true,\n" +
      "    \"has_downloads\": true,\n" +
      "    \"has_wiki\": false,\n" +
      "    \"has_pages\": false,\n" +
      "    \"forks_count\": 34,\n" +
      "    \"mirror_url\": null,\n" +
      "    \"open_issues_count\": 17,\n" +
      "    \"forks\": 34,\n" +
      "    \"open_issues\": 17,\n" +
      "    \"watchers\": 22,\n" +
      "    \"default_branch\": \"master\",\n" +
      "    \"permissions\": {\n" +
      "      \"admin\": false,\n" +
      "      \"push\": false,\n" +
      "      \"pull\": true\n" +
      "    }\n" +
      "  },\n" +
      "  {\n" +
      "    \"id\": 21415560,\n" +
      "    \"name\": \"vertx-lang-js\",\n" +
      "    \"full_name\": \"vert-x3/vertx-lang-js\",\n" +
      "    \"owner\": {\n" +
      "      \"login\": \"vert-x3\",\n" +
      "      \"id\": 8124623,\n" +
      "      \"avatar_url\": \"https://avatars.githubusercontent.com/u/8124623?v=3\",\n" +
      "      \"gravatar_id\": \"\",\n" +
      "      \"url\": \"https://api.github.com/users/vert-x3\",\n" +
      "      \"html_url\": \"https://github.com/vert-x3\",\n" +
      "      \"followers_url\": \"https://api.github.com/users/vert-x3/followers\",\n" +
      "      \"following_url\": \"https://api.github.com/users/vert-x3/following{/other_user}\",\n" +
      "      \"gists_url\": \"https://api.github.com/users/vert-x3/gists{/gist_id}\",\n" +
      "      \"starred_url\": \"https://api.github.com/users/vert-x3/starred{/owner}{/repo}\",\n" +
      "      \"subscriptions_url\": \"https://api.github.com/users/vert-x3/subscriptions\",\n" +
      "      \"organizations_url\": \"https://api.github.com/users/vert-x3/orgs\",\n" +
      "      \"repos_url\": \"https://api.github.com/users/vert-x3/repos\",\n" +
      "      \"events_url\": \"https://api.github.com/users/vert-x3/events{/privacy}\",\n" +
      "      \"received_events_url\": \"https://api.github.com/users/vert-x3/received_events\",\n" +
      "      \"type\": \"Organization\",\n" +
      "      \"site_admin\": false\n" +
      "    },\n" +
      "    \"private\": false,\n" +
      "    \"html_url\": \"https://github.com/vert-x3/vertx-lang-js\",\n" +
      "    \"description\": null,\n" +
      "    \"fork\": false,\n" +
      "    \"url\": \"https://api.github.com/repos/vert-x3/vertx-lang-js\",\n" +
      "    \"forks_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-js/forks\",\n" +
      "    \"keys_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-js/keys{/key_id}\",\n" +
      "    \"collaborators_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-js/collaborators{/collaborator}\",\n" +
      "    \"teams_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-js/teams\",\n" +
      "    \"hooks_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-js/hooks\",\n" +
      "    \"issue_events_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-js/issues/events{/number}\",\n" +
      "    \"events_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-js/events\",\n" +
      "    \"assignees_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-js/assignees{/user}\",\n" +
      "    \"branches_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-js/branches{/branch}\",\n" +
      "    \"tags_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-js/tags\",\n" +
      "    \"blobs_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-js/git/blobs{/sha}\",\n" +
      "    \"git_tags_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-js/git/tags{/sha}\",\n" +
      "    \"git_refs_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-js/git/refs{/sha}\",\n" +
      "    \"trees_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-js/git/trees{/sha}\",\n" +
      "    \"statuses_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-js/statuses/{sha}\",\n" +
      "    \"languages_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-js/languages\",\n" +
      "    \"stargazers_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-js/stargazers\",\n" +
      "    \"contributors_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-js/contributors\",\n" +
      "    \"subscribers_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-js/subscribers\",\n" +
      "    \"subscription_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-js/subscription\",\n" +
      "    \"commits_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-js/commits{/sha}\",\n" +
      "    \"git_commits_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-js/git/commits{/sha}\",\n" +
      "    \"comments_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-js/comments{/number}\",\n" +
      "    \"issue_comment_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-js/issues/comments{/number}\",\n" +
      "    \"contents_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-js/contents/{+path}\",\n" +
      "    \"compare_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-js/compare/{base}...{head}\",\n" +
      "    \"merges_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-js/merges\",\n" +
      "    \"archive_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-js/{archive_format}{/ref}\",\n" +
      "    \"downloads_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-js/downloads\",\n" +
      "    \"issues_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-js/issues{/number}\",\n" +
      "    \"pulls_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-js/pulls{/number}\",\n" +
      "    \"milestones_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-js/milestones{/number}\",\n" +
      "    \"notifications_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-js/notifications{?since,all,participating}\",\n" +
      "    \"labels_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-js/labels{/name}\",\n" +
      "    \"releases_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-js/releases{/id}\",\n" +
      "    \"deployments_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-js/deployments\",\n" +
      "    \"created_at\": \"2014-07-02T07:40:15Z\",\n" +
      "    \"updated_at\": \"2016-09-18T10:11:21Z\",\n" +
      "    \"pushed_at\": \"2016-10-25T16:13:55Z\",\n" +
      "    \"git_url\": \"git://github.com/vert-x3/vertx-lang-js.git\",\n" +
      "    \"ssh_url\": \"git@github.com:vert-x3/vertx-lang-js.git\",\n" +
      "    \"clone_url\": \"https://github.com/vert-x3/vertx-lang-js.git\",\n" +
      "    \"svn_url\": \"https://github.com/vert-x3/vertx-lang-js\",\n" +
      "    \"homepage\": null,\n" +
      "    \"size\": 2871,\n" +
      "    \"stargazers_count\": 15,\n" +
      "    \"watchers_count\": 15,\n" +
      "    \"language\": \"JavaScript\",\n" +
      "    \"has_issues\": true,\n" +
      "    \"has_downloads\": true,\n" +
      "    \"has_wiki\": false,\n" +
      "    \"has_pages\": false,\n" +
      "    \"forks_count\": 17,\n" +
      "    \"mirror_url\": null,\n" +
      "    \"open_issues_count\": 5,\n" +
      "    \"forks\": 17,\n" +
      "    \"open_issues\": 5,\n" +
      "    \"watchers\": 15,\n" +
      "    \"default_branch\": \"master\",\n" +
      "    \"permissions\": {\n" +
      "      \"admin\": false,\n" +
      "      \"push\": false,\n" +
      "      \"pull\": true\n" +
      "    }\n" +
      "  },\n" +
      "  {\n" +
      "    \"id\": 21979411,\n" +
      "    \"name\": \"vertx-lang-groovy\",\n" +
      "    \"full_name\": \"vert-x3/vertx-lang-groovy\",\n" +
      "    \"owner\": {\n" +
      "      \"login\": \"vert-x3\",\n" +
      "      \"id\": 8124623,\n" +
      "      \"avatar_url\": \"https://avatars.githubusercontent.com/u/8124623?v=3\",\n" +
      "      \"gravatar_id\": \"\",\n" +
      "      \"url\": \"https://api.github.com/users/vert-x3\",\n" +
      "      \"html_url\": \"https://github.com/vert-x3\",\n" +
      "      \"followers_url\": \"https://api.github.com/users/vert-x3/followers\",\n" +
      "      \"following_url\": \"https://api.github.com/users/vert-x3/following{/other_user}\",\n" +
      "      \"gists_url\": \"https://api.github.com/users/vert-x3/gists{/gist_id}\",\n" +
      "      \"starred_url\": \"https://api.github.com/users/vert-x3/starred{/owner}{/repo}\",\n" +
      "      \"subscriptions_url\": \"https://api.github.com/users/vert-x3/subscriptions\",\n" +
      "      \"organizations_url\": \"https://api.github.com/users/vert-x3/orgs\",\n" +
      "      \"repos_url\": \"https://api.github.com/users/vert-x3/repos\",\n" +
      "      \"events_url\": \"https://api.github.com/users/vert-x3/events{/privacy}\",\n" +
      "      \"received_events_url\": \"https://api.github.com/users/vert-x3/received_events\",\n" +
      "      \"type\": \"Organization\",\n" +
      "      \"site_admin\": false\n" +
      "    },\n" +
      "    \"private\": false,\n" +
      "    \"html_url\": \"https://github.com/vert-x3/vertx-lang-groovy\",\n" +
      "    \"description\": \"Vert.x Groovy support\",\n" +
      "    \"fork\": false,\n" +
      "    \"url\": \"https://api.github.com/repos/vert-x3/vertx-lang-groovy\",\n" +
      "    \"forks_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-groovy/forks\",\n" +
      "    \"keys_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-groovy/keys{/key_id}\",\n" +
      "    \"collaborators_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-groovy/collaborators{/collaborator}\",\n" +
      "    \"teams_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-groovy/teams\",\n" +
      "    \"hooks_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-groovy/hooks\",\n" +
      "    \"issue_events_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-groovy/issues/events{/number}\",\n" +
      "    \"events_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-groovy/events\",\n" +
      "    \"assignees_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-groovy/assignees{/user}\",\n" +
      "    \"branches_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-groovy/branches{/branch}\",\n" +
      "    \"tags_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-groovy/tags\",\n" +
      "    \"blobs_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-groovy/git/blobs{/sha}\",\n" +
      "    \"git_tags_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-groovy/git/tags{/sha}\",\n" +
      "    \"git_refs_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-groovy/git/refs{/sha}\",\n" +
      "    \"trees_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-groovy/git/trees{/sha}\",\n" +
      "    \"statuses_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-groovy/statuses/{sha}\",\n" +
      "    \"languages_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-groovy/languages\",\n" +
      "    \"stargazers_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-groovy/stargazers\",\n" +
      "    \"contributors_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-groovy/contributors\",\n" +
      "    \"subscribers_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-groovy/subscribers\",\n" +
      "    \"subscription_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-groovy/subscription\",\n" +
      "    \"commits_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-groovy/commits{/sha}\",\n" +
      "    \"git_commits_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-groovy/git/commits{/sha}\",\n" +
      "    \"comments_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-groovy/comments{/number}\",\n" +
      "    \"issue_comment_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-groovy/issues/comments{/number}\",\n" +
      "    \"contents_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-groovy/contents/{+path}\",\n" +
      "    \"compare_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-groovy/compare/{base}...{head}\",\n" +
      "    \"merges_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-groovy/merges\",\n" +
      "    \"archive_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-groovy/{archive_format}{/ref}\",\n" +
      "    \"downloads_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-groovy/downloads\",\n" +
      "    \"issues_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-groovy/issues{/number}\",\n" +
      "    \"pulls_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-groovy/pulls{/number}\",\n" +
      "    \"milestones_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-groovy/milestones{/number}\",\n" +
      "    \"notifications_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-groovy/notifications{?since,all,participating}\",\n" +
      "    \"labels_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-groovy/labels{/name}\",\n" +
      "    \"releases_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-groovy/releases{/id}\",\n" +
      "    \"deployments_url\": \"https://api.github.com/repos/vert-x3/vertx-lang-groovy/deployments\",\n" +
      "    \"created_at\": \"2014-07-18T12:21:17Z\",\n" +
      "    \"updated_at\": \"2016-09-18T10:12:11Z\",\n" +
      "    \"pushed_at\": \"2016-10-28T07:50:56Z\",\n" +
      "    \"git_url\": \"git://github.com/vert-x3/vertx-lang-groovy.git\",\n" +
      "    \"ssh_url\": \"git@github.com:vert-x3/vertx-lang-groovy.git\",\n" +
      "    \"clone_url\": \"https://github.com/vert-x3/vertx-lang-groovy.git\",\n" +
      "    \"svn_url\": \"https://github.com/vert-x3/vertx-lang-groovy\",\n" +
      "    \"homepage\": \"\",\n" +
      "    \"size\": 1231,\n" +
      "    \"stargazers_count\": 12,\n" +
      "    \"watchers_count\": 12,\n" +
      "    \"language\": \"Groovy\",\n" +
      "    \"has_issues\": true,\n" +
      "    \"has_downloads\": true,\n" +
      "    \"has_wiki\": false,\n" +
      "    \"has_pages\": false,\n" +
      "    \"forks_count\": 14,\n" +
      "    \"mirror_url\": null,\n" +
      "    \"open_issues_count\": 6,\n" +
      "    \"forks\": 14,\n" +
      "    \"open_issues\": 6,\n" +
      "    \"watchers\": 12,\n" +
      "    \"default_branch\": \"master\",\n" +
      "    \"permissions\": {\n" +
      "      \"admin\": false,\n" +
      "      \"push\": false,\n" +
      "      \"pull\": true\n" +
      "    }\n" +
      "  },\n" +
      "  {\n" +
      "    \"id\": 22686950,\n" +
      "    \"name\": \"vertx-stack\",\n" +
      "    \"full_name\": \"vert-x3/vertx-stack\",\n" +
      "    \"owner\": {\n" +
      "      \"login\": \"vert-x3\",\n" +
      "      \"id\": 8124623,\n" +
      "      \"avatar_url\": \"https://avatars.githubusercontent.com/u/8124623?v=3\",\n" +
      "      \"gravatar_id\": \"\",\n" +
      "      \"url\": \"https://api.github.com/users/vert-x3\",\n" +
      "      \"html_url\": \"https://github.com/vert-x3\",\n" +
      "      \"followers_url\": \"https://api.github.com/users/vert-x3/followers\",\n" +
      "      \"following_url\": \"https://api.github.com/users/vert-x3/following{/other_user}\",\n" +
      "      \"gists_url\": \"https://api.github.com/users/vert-x3/gists{/gist_id}\",\n" +
      "      \"starred_url\": \"https://api.github.com/users/vert-x3/starred{/owner}{/repo}\",\n" +
      "      \"subscriptions_url\": \"https://api.github.com/users/vert-x3/subscriptions\",\n" +
      "      \"organizations_url\": \"https://api.github.com/users/vert-x3/orgs\",\n" +
      "      \"repos_url\": \"https://api.github.com/users/vert-x3/repos\",\n" +
      "      \"events_url\": \"https://api.github.com/users/vert-x3/events{/privacy}\",\n" +
      "      \"received_events_url\": \"https://api.github.com/users/vert-x3/received_events\",\n" +
      "      \"type\": \"Organization\",\n" +
      "      \"site_admin\": false\n" +
      "    },\n" +
      "    \"private\": false,\n" +
      "    \"html_url\": \"https://github.com/vert-x3/vertx-stack\",\n" +
      "    \"description\": \"Vert.x stack\",\n" +
      "    \"fork\": false,\n" +
      "    \"url\": \"https://api.github.com/repos/vert-x3/vertx-stack\",\n" +
      "    \"forks_url\": \"https://api.github.com/repos/vert-x3/vertx-stack/forks\",\n" +
      "    \"keys_url\": \"https://api.github.com/repos/vert-x3/vertx-stack/keys{/key_id}\",\n" +
      "    \"collaborators_url\": \"https://api.github.com/repos/vert-x3/vertx-stack/collaborators{/collaborator}\",\n" +
      "    \"teams_url\": \"https://api.github.com/repos/vert-x3/vertx-stack/teams\",\n" +
      "    \"hooks_url\": \"https://api.github.com/repos/vert-x3/vertx-stack/hooks\",\n" +
      "    \"issue_events_url\": \"https://api.github.com/repos/vert-x3/vertx-stack/issues/events{/number}\",\n" +
      "    \"events_url\": \"https://api.github.com/repos/vert-x3/vertx-stack/events\",\n" +
      "    \"assignees_url\": \"https://api.github.com/repos/vert-x3/vertx-stack/assignees{/user}\",\n" +
      "    \"branches_url\": \"https://api.github.com/repos/vert-x3/vertx-stack/branches{/branch}\",\n" +
      "    \"tags_url\": \"https://api.github.com/repos/vert-x3/vertx-stack/tags\",\n" +
      "    \"blobs_url\": \"https://api.github.com/repos/vert-x3/vertx-stack/git/blobs{/sha}\",\n" +
      "    \"git_tags_url\": \"https://api.github.com/repos/vert-x3/vertx-stack/git/tags{/sha}\",\n" +
      "    \"git_refs_url\": \"https://api.github.com/repos/vert-x3/vertx-stack/git/refs{/sha}\",\n" +
      "    \"trees_url\": \"https://api.github.com/repos/vert-x3/vertx-stack/git/trees{/sha}\",\n" +
      "    \"statuses_url\": \"https://api.github.com/repos/vert-x3/vertx-stack/statuses/{sha}\",\n" +
      "    \"languages_url\": \"https://api.github.com/repos/vert-x3/vertx-stack/languages\",\n" +
      "    \"stargazers_url\": \"https://api.github.com/repos/vert-x3/vertx-stack/stargazers\",\n" +
      "    \"contributors_url\": \"https://api.github.com/repos/vert-x3/vertx-stack/contributors\",\n" +
      "    \"subscribers_url\": \"https://api.github.com/repos/vert-x3/vertx-stack/subscribers\",\n" +
      "    \"subscription_url\": \"https://api.github.com/repos/vert-x3/vertx-stack/subscription\",\n" +
      "    \"commits_url\": \"https://api.github.com/repos/vert-x3/vertx-stack/commits{/sha}\",\n" +
      "    \"git_commits_url\": \"https://api.github.com/repos/vert-x3/vertx-stack/git/commits{/sha}\",\n" +
      "    \"comments_url\": \"https://api.github.com/repos/vert-x3/vertx-stack/comments{/number}\",\n" +
      "    \"issue_comment_url\": \"https://api.github.com/repos/vert-x3/vertx-stack/issues/comments{/number}\",\n" +
      "    \"contents_url\": \"https://api.github.com/repos/vert-x3/vertx-stack/contents/{+path}\",\n" +
      "    \"compare_url\": \"https://api.github.com/repos/vert-x3/vertx-stack/compare/{base}...{head}\",\n" +
      "    \"merges_url\": \"https://api.github.com/repos/vert-x3/vertx-stack/merges\",\n" +
      "    \"archive_url\": \"https://api.github.com/repos/vert-x3/vertx-stack/{archive_format}{/ref}\",\n" +
      "    \"downloads_url\": \"https://api.github.com/repos/vert-x3/vertx-stack/downloads\",\n" +
      "    \"issues_url\": \"https://api.github.com/repos/vert-x3/vertx-stack/issues{/number}\",\n" +
      "    \"pulls_url\": \"https://api.github.com/repos/vert-x3/vertx-stack/pulls{/number}\",\n" +
      "    \"milestones_url\": \"https://api.github.com/repos/vert-x3/vertx-stack/milestones{/number}\",\n" +
      "    \"notifications_url\": \"https://api.github.com/repos/vert-x3/vertx-stack/notifications{?since,all,participating}\",\n" +
      "    \"labels_url\": \"https://api.github.com/repos/vert-x3/vertx-stack/labels{/name}\",\n" +
      "    \"releases_url\": \"https://api.github.com/repos/vert-x3/vertx-stack/releases{/id}\",\n" +
      "    \"deployments_url\": \"https://api.github.com/repos/vert-x3/vertx-stack/deployments\",\n" +
      "    \"created_at\": \"2014-08-06T15:05:50Z\",\n" +
      "    \"updated_at\": \"2016-10-01T09:05:29Z\",\n" +
      "    \"pushed_at\": \"2016-10-13T09:49:42Z\",\n" +
      "    \"git_url\": \"git://github.com/vert-x3/vertx-stack.git\",\n" +
      "    \"ssh_url\": \"git@github.com:vert-x3/vertx-stack.git\",\n" +
      "    \"clone_url\": \"https://github.com/vert-x3/vertx-stack.git\",\n" +
      "    \"svn_url\": \"https://github.com/vert-x3/vertx-stack\",\n" +
      "    \"homepage\": null,\n" +
      "    \"size\": 561,\n" +
      "    \"stargazers_count\": 61,\n" +
      "    \"watchers_count\": 61,\n" +
      "    \"language\": \"Java\",\n" +
      "    \"has_issues\": true,\n" +
      "    \"has_downloads\": true,\n" +
      "    \"has_wiki\": true,\n" +
      "    \"has_pages\": false,\n" +
      "    \"forks_count\": 29,\n" +
      "    \"mirror_url\": null,\n" +
      "    \"open_issues_count\": 3,\n" +
      "    \"forks\": 29,\n" +
      "    \"open_issues\": 3,\n" +
      "    \"watchers\": 61,\n" +
      "    \"default_branch\": \"master\",\n" +
      "    \"permissions\": {\n" +
      "      \"admin\": false,\n" +
      "      \"push\": false,\n" +
      "      \"pull\": true\n" +
      "    }\n" +
      "  }\n" +
      "]";

  @Test
  public void test() {
    List<Repository> list = Json.fromJsonAsList(response, Repository.class);
    assertThat(list).isNotEmpty();
    for (Repository s : list) {
      assertThat(s.getId()).isNotZero();
      assertThat(s.getFullName()).isNotEmpty();
      assertThat(s.getName()).isNotEmpty();
      assertThat(s.getUrl()).isNotEmpty();
    }
  }

}
