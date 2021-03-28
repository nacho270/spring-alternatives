package com.nacho.blog.springalternatives.retrofit;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Post {

  public Integer id;
  public Integer userId;
  public String title;
  public String body;

  public static Post postWith(final Integer userId, final String title, final String body) {
    return new Post(null, userId, title, body);
  }
}