package com.nacho.blog.springalternatives.okhttp;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class Application {

  private final JsonPlaceholderPostService postService;

  public Application() {
    postService = new JsonPlaceholderPostService();
  }

  public Post getPostById(final Integer id) {
    try {
      return postService.getPostById(id);
    } catch (final Exception e) {
      throw new RuntimeException("Error getting post");
    }
  }

  public CompletableFuture<List<Post>> getAllPostsAsync() {
    return postService.getAllPostsAsync();
  }

  public List<Post> getAllPostsSync() {
    try {
      // trim the resopnse to just 3 for readability
      return postService.getPosts().stream().limit(3L).collect(Collectors.toList());
    } catch (final Exception e) {
      throw new RuntimeException("Error getting all posts");
    }
  }

  public Post createPost(final String title, final String body, final Integer userId) {
    try {
      return postService.createPost(Post.postWith(userId, title, body));
    } catch (final Exception e) {
      throw new RuntimeException("Error getting creating post");
    }
  }

  public List<Post> getAllPostsForUserSync(final Integer userId) {
    try {
      // trim the resopnse to just 3 for readability
      return postService.getPostsForUser(userId).stream().limit(3L).collect(Collectors.toList());
    } catch (final Exception e) {
      throw new RuntimeException("Error getting all posts");
    }
  }

  public void finish() {
    postService.shutdown();
  }
}
