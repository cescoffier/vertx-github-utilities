package me.escoffier.vertx.github.model;

import me.escoffier.vertx.github.utils.Json;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class ContributorTest {

  String response = "[\n" +
      "  {\n" +
      "    \"login\": \"cescoffier\",\n" +
      "    \"id\": 402301,\n" +
      "    \"avatar_url\": \"https://avatars.githubusercontent.com/u/402301?v=3\",\n" +
      "    \"gravatar_id\": \"\",\n" +
      "    \"url\": \"https://api.github.com/users/cescoffier\",\n" +
      "    \"html_url\": \"https://github.com/cescoffier\",\n" +
      "    \"followers_url\": \"https://api.github.com/users/cescoffier/followers\",\n" +
      "    \"following_url\": \"https://api.github.com/users/cescoffier/following{/other_user}\",\n" +
      "    \"gists_url\": \"https://api.github.com/users/cescoffier/gists{/gist_id}\",\n" +
      "    \"starred_url\": \"https://api.github.com/users/cescoffier/starred{/owner}{/repo}\",\n" +
      "    \"subscriptions_url\": \"https://api.github.com/users/cescoffier/subscriptions\",\n" +
      "    \"organizations_url\": \"https://api.github.com/users/cescoffier/orgs\",\n" +
      "    \"repos_url\": \"https://api.github.com/users/cescoffier/repos\",\n" +
      "    \"events_url\": \"https://api.github.com/users/cescoffier/events{/privacy}\",\n" +
      "    \"received_events_url\": \"https://api.github.com/users/cescoffier/received_events\",\n" +
      "    \"type\": \"User\",\n" +
      "    \"site_admin\": false,\n" +
      "    \"contributions\": 10\n" +
      "  },\n" +
      "  {\n" +
      "    \"login\": \"pmlopes\",\n" +
      "    \"id\": 849467,\n" +
      "    \"avatar_url\": \"https://avatars.githubusercontent.com/u/849467?v=3\",\n" +
      "    \"gravatar_id\": \"\",\n" +
      "    \"url\": \"https://api.github.com/users/pmlopes\",\n" +
      "    \"html_url\": \"https://github.com/pmlopes\",\n" +
      "    \"followers_url\": \"https://api.github.com/users/pmlopes/followers\",\n" +
      "    \"following_url\": \"https://api.github.com/users/pmlopes/following{/other_user}\",\n" +
      "    \"gists_url\": \"https://api.github.com/users/pmlopes/gists{/gist_id}\",\n" +
      "    \"starred_url\": \"https://api.github.com/users/pmlopes/starred{/owner}{/repo}\",\n" +
      "    \"subscriptions_url\": \"https://api.github.com/users/pmlopes/subscriptions\",\n" +
      "    \"organizations_url\": \"https://api.github.com/users/pmlopes/orgs\",\n" +
      "    \"repos_url\": \"https://api.github.com/users/pmlopes/repos\",\n" +
      "    \"events_url\": \"https://api.github.com/users/pmlopes/events{/privacy}\",\n" +
      "    \"received_events_url\": \"https://api.github.com/users/pmlopes/received_events\",\n" +
      "    \"type\": \"User\",\n" +
      "    \"site_admin\": false,\n" +
      "    \"contributions\": 1\n" +
      "  }\n" +
      "]";


  @Test
  public void test() {
    List<Contributor> list = Json.fromJsonAsList(response, Contributor.class);
    assertThat(list).hasSize(2);
    for (Contributor s : list) {
      assertThat(s.getLogin()).isNotNull().isNotEmpty();
      assertThat(s.getContributions()).isNotZero();
    }
  }

}
