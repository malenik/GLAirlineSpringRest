package com.malenko.rest.model;

import lombok.Data;

@Data
public class Response<T> {
  private T content;
  private Integer status;
  private String message;

  public Response(T content, Integer status, String message) {
    this.content = content;
    this.status = status;
    this.message = message;
  }

  public Response(T content) {
    this.content = content;
  }

  public Response() {
  }

  public static <T> Response<T> Ok() {
    return new Response<>(null, 200, "success");
  }

  public static <T> Response<T> Ok(T content) {
    return new Response<>(content, 200, "success");
  }

  public static <T> Response<T> Created(T content) {
    return new Response<>(content, 201, "success");
  }

  public static <T> Response<T> NotFound(String message) {
    return new Response<>(null, 404, message);
  }

  public static <T> Response<T> BadRequest(String message) {
    return new Response<>(null, 400, message);
  }
}
