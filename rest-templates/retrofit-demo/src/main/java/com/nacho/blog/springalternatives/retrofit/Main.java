package com.nacho.blog.springalternatives.retrofit;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {

  public static void main(final String[] args) throws IOException {
    final Application application = new Application();
    log.info("Getting posts Sync");
    application.getAllPostsSync().forEach(p -> log.info("{}", p));
    log.info("====================================================================================================");
    log.info("Getting posts Async");
    application.getAllPostsAsync() //
        .whenComplete((posts, e) -> {
          log.info("GOT POSTS ASYNC!");
          posts.forEach(p -> log.info("{}", p));
        }).join();
    log.info("====================================================================================================");
    log.info("Post with id 1: {}", application.getPostById(1));
    log.info("====================================================================================================");
    log.info("Creating post..");
    final Post post = application.createPost("My title", "The body", 777);
    log.info("Created post: {}", post);
    log.info("====================================================================================================");
    log.info("Getting posts for user 1");
    application.getAllPostsForUserSync(1).forEach(p -> log.info("{}", p));
    application.finish();
  }
}
