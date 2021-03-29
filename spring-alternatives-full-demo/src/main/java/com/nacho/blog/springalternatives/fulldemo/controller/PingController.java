package com.nacho.blog.springalternatives.fulldemo.controller;

import javax.inject.Inject;

public class PingController {

  @Inject
  public PingController() {
  }

  public String handle() {
    return "pong";
  }
}
