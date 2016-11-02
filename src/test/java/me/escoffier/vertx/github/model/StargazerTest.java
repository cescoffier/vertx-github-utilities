package me.escoffier.vertx.github.model;

import me.escoffier.vertx.github.utils.Json;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class StargazerTest {

  String response = "[\n" +
      "  {\n" +
      "    \"starred_at\": \"2016-10-16T11:53:08Z\",\n" +
      "    \"user\": {\n" +
      "      \"login\": \"cescoffier\",\n" +
      "      \"id\": 402301,\n" +
      "      \"avatar_url\": \"https://avatars.githubusercontent.com/u/402301?v=3\",\n" +
      "      \"gravatar_id\": \"\",\n" +
      "      \"url\": \"https://api.github.com/users/cescoffier\",\n" +
      "      \"html_url\": \"https://github.com/cescoffier\",\n" +
      "      \"followers_url\": \"https://api.github.com/users/cescoffier/followers\",\n" +
      "      \"following_url\": \"https://api.github.com/users/cescoffier/following{/other_user}\",\n" +
      "      \"gists_url\": \"https://api.github.com/users/cescoffier/gists{/gist_id}\",\n" +
      "      \"starred_url\": \"https://api.github.com/users/cescoffier/starred{/owner}{/repo}\",\n" +
      "      \"subscriptions_url\": \"https://api.github.com/users/cescoffier/subscriptions\",\n" +
      "      \"organizations_url\": \"https://api.github.com/users/cescoffier/orgs\",\n" +
      "      \"repos_url\": \"https://api.github.com/users/cescoffier/repos\",\n" +
      "      \"events_url\": \"https://api.github.com/users/cescoffier/events{/privacy}\",\n" +
      "      \"received_events_url\": \"https://api.github.com/users/cescoffier/received_events\",\n" +
      "      \"type\": \"User\",\n" +
      "      \"site_admin\": false\n" +
      "    }\n" +
      "  },\n" +
      "  {\n" +
      "    \"starred_at\": \"2016-10-17T08:03:31Z\",\n" +
      "    \"user\": {\n" +
      "      \"login\": \"JGrancha\",\n" +
      "      \"id\": 5517910,\n" +
      "      \"avatar_url\": \"https://avatars.githubusercontent.com/u/5517910?v=3\",\n" +
      "      \"gravatar_id\": \"\",\n" +
      "      \"url\": \"https://api.github.com/users/JGrancha\",\n" +
      "      \"html_url\": \"https://github.com/JGrancha\",\n" +
      "      \"followers_url\": \"https://api.github.com/users/JGrancha/followers\",\n" +
      "      \"following_url\": \"https://api.github.com/users/JGrancha/following{/other_user}\",\n" +
      "      \"gists_url\": \"https://api.github.com/users/JGrancha/gists{/gist_id}\",\n" +
      "      \"starred_url\": \"https://api.github.com/users/JGrancha/starred{/owner}{/repo}\",\n" +
      "      \"subscriptions_url\": \"https://api.github.com/users/JGrancha/subscriptions\",\n" +
      "      \"organizations_url\": \"https://api.github.com/users/JGrancha/orgs\",\n" +
      "      \"repos_url\": \"https://api.github.com/users/JGrancha/repos\",\n" +
      "      \"events_url\": \"https://api.github.com/users/JGrancha/events{/privacy}\",\n" +
      "      \"received_events_url\": \"https://api.github.com/users/JGrancha/received_events\",\n" +
      "      \"type\": \"User\",\n" +
      "      \"site_admin\": false\n" +
      "    }\n" +
      "  },\n" +
      "  {\n" +
      "    \"starred_at\": \"2016-10-18T06:38:10Z\",\n" +
      "    \"user\": {\n" +
      "      \"login\": \"remi-parain\",\n" +
      "      \"id\": 2346921,\n" +
      "      \"avatar_url\": \"https://avatars.githubusercontent.com/u/2346921?v=3\",\n" +
      "      \"gravatar_id\": \"\",\n" +
      "      \"url\": \"https://api.github.com/users/remi-parain\",\n" +
      "      \"html_url\": \"https://github.com/remi-parain\",\n" +
      "      \"followers_url\": \"https://api.github.com/users/remi-parain/followers\",\n" +
      "      \"following_url\": \"https://api.github.com/users/remi-parain/following{/other_user}\",\n" +
      "      \"gists_url\": \"https://api.github.com/users/remi-parain/gists{/gist_id}\",\n" +
      "      \"starred_url\": \"https://api.github.com/users/remi-parain/starred{/owner}{/repo}\",\n" +
      "      \"subscriptions_url\": \"https://api.github.com/users/remi-parain/subscriptions\",\n" +
      "      \"organizations_url\": \"https://api.github.com/users/remi-parain/orgs\",\n" +
      "      \"repos_url\": \"https://api.github.com/users/remi-parain/repos\",\n" +
      "      \"events_url\": \"https://api.github.com/users/remi-parain/events{/privacy}\",\n" +
      "      \"received_events_url\": \"https://api.github.com/users/remi-parain/received_events\",\n" +
      "      \"type\": \"User\",\n" +
      "      \"site_admin\": false\n" +
      "    }\n" +
      "  }\n" +
      "]\n";


  @Test
  public void test() {
    List<Stargazer> list = Json.fromJsonAsList(response, Stargazer.class);
    assertThat(list).hasSize(3);
    for (Stargazer s : list) {
      assertThat(s.getUser().getLogin()).isNotNull().isNotEmpty();
      assertThat(s.getDate()).isNotNull().isNotEmpty();
      assertThat(s.getUser().getId()).isNotZero();
    }
  }

}
