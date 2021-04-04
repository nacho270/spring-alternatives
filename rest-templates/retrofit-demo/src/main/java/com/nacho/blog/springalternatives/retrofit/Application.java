package com.nacho.blog.springalternatives.retrofit;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Application {

  private final JsonPlaceholderPostService postService;

  public Application() {
    final Retrofit retrofit = new Retrofit.Builder() //
        .baseUrl("https://jsonplaceholder.typicode.com/") //
        .addConverterFactory(GsonConverterFactory.create()) //
        .build();
    postService = retrofit.create(JsonPlaceholderPostService.class);
  }

  public CompletableFuture<List<Post>> getAllPostsAsync() throws IOException {
    final CompletableFuture<List<Post>> futurePosts = new CompletableFuture<>();
    postService.getPosts().enqueue(new Callback<List<Post>>() {

      @Override
      public void onResponse(final Call<List<Post>> call, final Response<List<Post>> response) {
        if (response.isSuccessful()) {
          // trim the resopnse to just 3 for readability
          futurePosts.complete(response.body().stream().limit(3L).collect(Collectors.toList()));
        }
      }

      @Override
      public void onFailure(final Call<List<Post>> call, final Throwable t) {
        throw new RuntimeException("Error getting all posts async!!");
      }
    });
    return futurePosts;
  }

  public Post getPostById(final Integer id) throws IOException {
    final Response<Post> response = postService.getPostById(id).execute();
    if (response.isSuccessful()) {
      return response.body();
    }
    throw new RuntimeException("Error getting post with id: " + id);
  }

  public List<Post> getAllPostsSync() throws IOException {
    final Response<List<Post>> response = postService.getPosts().execute();
    if (response.isSuccessful()) {
      // trim the resopnse to just 3 for readability
      return response.body().stream().limit(3L).collect(Collectors.toList());
    }
    throw new RuntimeException("Error getting all posts");
  }

  public Post createPost(final String title, final String body, final Integer userId) throws IOException {
    final Response<Post> response = postService.createPost(Post.postWith(userId, title, body)).execute();
    if (response.isSuccessful()) {
      return response.body();
    }
    throw new RuntimeException("Error creating post");
  }

  public List<Post> getAllPostsForUserSync(final Integer userId) throws IOException {
    final Response<List<Post>> response = postService.getPostsForUser(userId).execute();
    if (response.isSuccessful()) {
      // trim the resopnse to just 3 for readability
      return response.body().stream().limit(3L).collect(Collectors.toList());
    }
    throw new RuntimeException("Error getting all posts");
  }

  public void finish() {
    System.exit(0);
  }
}
