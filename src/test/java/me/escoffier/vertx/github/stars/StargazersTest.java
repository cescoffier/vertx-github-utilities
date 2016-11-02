package me.escoffier.vertx.github.stars;

import me.escoffier.vertx.github.model.Stargazer;
import me.escoffier.vertx.github.release.Project;
import me.escoffier.vertx.github.services.StargazerService;
import org.junit.Test;

import java.io.IOException;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class StargazersTest {


  @Test
  public void testStargazers() throws IOException {
    Set<Stargazer> stargazers = StargazerService.stargazers(new Project("vertx-zookeeper",
        "vert-x3"));

    for (Stargazer stargazer : stargazers) {
      assertThat(stargazer.getUser().getLogin()).isNotEmpty();
      assertThat(stargazer.getDate()).isNotNull();
      assertThat(stargazer.getProject()).isEqualTo("vert-x3/vertx-zookeeper");
    }
  }

}