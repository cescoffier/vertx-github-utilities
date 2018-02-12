package me.escoffier.vertx.github.utils;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class GithubClient {

  public static final String ROOT = "https://api.github.com/repos";
  public static final String ROOT_ORGS = "https://api.github.com/orgs";

  private final String token;
  private OkHttpClient client = new OkHttpClient();

  public GithubClient(String token) {
    this.token = token;
  }


  public <T> T request(String url, Class<T> clazz) throws IOException {
    Request request = new Request.Builder()
        .url(url)
        .addHeader("Authorization", "token " + token)
        .addHeader("Accept", "application/vnd.github.v3.star+json")
        .build();

    Response response = client.newCall(request).execute();
    String content = response.body().string();
    return Json.fromJson(content, clazz);
  }

  private String request(String org, String repo, String suffix, int page, Map<String, String> params) throws
      IOException {
    String url = ROOT;
    if (repo == null) {
      url =  ROOT_ORGS;
    }
    if (org != null) {
      url += "/" + org;
    }

    if (repo != null) {
      url += "/" + repo;
    }

    if (suffix != null) {
      url += "/" + suffix;
    }

    url = url + "?page=" + page;

    StringBuilder parameters = new StringBuilder();
    if (params != null) {
      params.entrySet().forEach(entry -> parameters.append("&").append(entry.getKey()).append("=").append(entry.getValue()));
      url += parameters.toString();
    }

    assert token != null;
    Request request = new Request.Builder()
        .url(url)
        .addHeader("Authorization", "token " + token)
        .addHeader("Accept", "application/vnd.github.v3.star+json")
        .build();

    Response response = client.newCall(request).execute();
    return response.body().string();
  }

  public List<String> request(String org, String repo, String suffix) throws IOException {
    int page = 0;
    List<String> res = new ArrayList<>();

    String response = request(org, repo, suffix, page, Collections.emptyMap());

    if (response.isEmpty() || response.equals("[]") || response.equals("{}")) {
      return res;
    }

    while (!response.isEmpty() && !response.equals("[]")) {
      page = page + 1;
      response = request(org, repo, suffix, page, Collections.emptyMap());
      if (!response.isEmpty() && !response.equals("[]") && !response.equals("{}")) {
        res.add(response);
      }
    }

    return res;
  }

  public List<String> request(String org, String repo, String suffix, Map<String, String> params) throws IOException {
    int page = 0;
    List<String> res = new ArrayList<>();

    String response = request(org, repo, suffix, page, params);

    if (response.isEmpty() || response.equals("[]") || response.equals("{}")) {
      return res;
    }

    while (!response.isEmpty() && !response.equals("[]")) {
      page = page + 1;
      response = request(org, repo, suffix, page, params);
      if (!response.isEmpty() && !response.equals("[]") && !response.equals("{}")) {
        res.add(response);
      }
    }

    return res;
  }

}
