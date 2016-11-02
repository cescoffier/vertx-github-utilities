package me.escoffier.vertx.github.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="http://escoffier.me">Clement Escoffier</a>
 */
public class Json {

  private static ObjectMapper mapper = new ObjectMapper();

  static {
    mapper = mapper
        .configure(SerializationFeature.CLOSE_CLOSEABLE, true)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .configure(JsonParser.Feature.ALLOW_COMMENTS, true)
        .configure(SerializationFeature.INDENT_OUTPUT, true);
  }

  public static <T> T fromJson(String json, Class<T> clazz) {
    try {
      return mapper.readValue(json, clazz);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> List<T> fromJsonAsList(String json, Class<T> clazz) {
    try {
      List<T> list = new ArrayList<>();
      ArrayNode nodes = mapper.readValue(json, ArrayNode.class);
      nodes.forEach(node -> {
        T t = fromJson(node.toString(), clazz);
        list.add(t);
      });
      return list;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> T fromJson(File file, Class<T> clazz) {
    try {
      return mapper.readValue(file, clazz);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static <T> String toJson(T object) {
    try {
      return mapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

}
