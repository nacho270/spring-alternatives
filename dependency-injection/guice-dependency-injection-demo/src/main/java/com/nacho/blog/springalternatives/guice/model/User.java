package com.nacho.blog.springalternatives.guice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {

  private int id;
  private String name;

}
