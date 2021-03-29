package com.nacho.blog.springalternatives.fulldemo.controller;

import spark.Request;
import spark.Response;

public class PingController {

  public static String handle(final Request request, final Response response) {
    return "pong";
  }
}
