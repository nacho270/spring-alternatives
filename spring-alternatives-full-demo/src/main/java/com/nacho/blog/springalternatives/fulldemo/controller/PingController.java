package com.nacho.blog.springalternatives.fulldemo.controller;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PingController {

  @Inject
  public PingController() {
  }

  public String handle() {
    return "pong";
  }
}
