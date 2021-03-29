package com.nacho.blog.springalternatives.fulldemo.controller;

import spark.Request;
import spark.Response;

import javax.inject.Inject;

public class PingController {

  @Inject
  public PingController() {
  }

  public String handle(final Request request, final Response response) {
    return "pong";
  }
}
