package com.nacho.blog.springalernatives.sparkweb;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Value;

@Value
public class User {
  int id;
  String name;

  @JsonCreator
  public User(@JsonProperty("id") final int id, @JsonProperty("name") final String name) {
    this.id = id;
    this.name = name;
  }
}