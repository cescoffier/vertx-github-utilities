package me.escoffier.vertx.github.utils;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class GithubClient {

  public static final String ROOT = "https://api.github.com/repos";

  private final String token;
  private OkHttpClient client = new OkHttpClient();

  public GithubClient(String token) {
    this.token = token;
  }


  public String request(String org, String repo, String suffix, int page) throws IOException {
    String url = ROOT;
    if (org != null) {
      url += "/" + org;
    }

    if (repo != null) {
      url += "/" + repo;
    }

    if (suffix != null) {
      url += "/" + suffix;
    }

    assert token != null;
    Request request = new Request.Builder()
        .url(url + "?page=" + page)
        .addHeader("Authorization", "token " + token)
        .addHeader("Accept", "application/vnd.github.v3.star+json")
        .build();

    Response response = client.newCall(request).execute();
    return response.body().string();
  }

  public List<String> request(String org, String repo, String suffix) throws IOException {
    int page = 0;
    List<String> res = new ArrayList<>();

    String response = request(org, repo, suffix, page);

    if (response.isEmpty() || response.equals("[]") || response.equals("{}")) {
      return res;
    }

    while (!response.isEmpty() && !response.equals("[]")) {
      page = page + 1;
      response = request(org, repo, suffix, page);
      if (! response.isEmpty() && ! response.equals("[]") && ! response.equals("{}")) {
        res.add(response);
      }
    }

    return res;
  }

}
