package com.nacho.blog.springalternatives.dagger.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {

  private int id;
  private String name;

}
