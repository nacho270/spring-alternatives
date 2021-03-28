package com.nacho.blog.springalternatives.okhttp;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class JsonPlaceholderPostService {

  private static final Gson GSON = new Gson();
  private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
  private static final Type POST_LIST_TYPE = new TypeToken<ArrayList<Post>>() {
  }.getType();

  private OkHttpClient client;

  public JsonPlaceholderPostService() {
    client = new OkHttpClient();
  }

  public CompletableFuture<List<Post>> getAllPostsAsync() {
    final CompletableFuture<List<Post>> futurePosts = new CompletableFuture<>();
    final Request request = new Request.Builder() //
        .url(BASE_URL + "posts") //
        .build();
    client.newCall(request).enqueue(new Callback() {

      @Override
      public void onResponse(final Call call, final Response response) throws IOException {
        if (response.isSuccessful()) {
          futurePosts.complete(GSON.fromJson(response.body().string(), POST_LIST_TYPE));
        }
      }

      @Override
      public void onFailure(final Call call, final IOException ex) {
        throw new RuntimeException("Error getting all posts async!!");
      }
    });
    return futurePosts;
  }

  public Post getPostById(final Integer postId) throws Exception {
    final Request request = new Request.Builder() //
        .url(BASE_URL + "posts/" + postId) //
        .build();
    try (Response response = client.newCall(request).execute()) {
      return GSON.fromJson(response.body().string(), Post.class);
    }
  }

  public List<Post> getPosts() throws Exception {
    final Request request = new Request.Builder() //
        .url(BASE_URL + "posts") //
        .build();
    try (Response response = client.newCall(request).execute()) {
      return GSON.fromJson(response.body().string(), POST_LIST_TYPE);
    }
  }

  public Post createPost(final Post post) throws Exception {
    final String json = GSON.toJson(post);
    final Request request = new Request.Builder() //
        .url(BASE_URL + "posts") //
        .post(RequestBody.Companion.create(json.getBytes()))//
        .build();
    try (Response response = client.newCall(request).execute()) {
      return GSON.fromJson(response.body().string(), Post.class);
    }
  }

  public List<Post> getPostsForUser(final Integer userId) throws Exception {
    final Request request = new Request.Builder() //
        .url(BASE_URL + "posts?userId=" + userId) //
        .build();
    try (Response response = client.newCall(request).execute()) {
      return GSON.fromJson(response.body().string(), POST_LIST_TYPE);
    }
  }

  public void shutdown() {
    client.dispatcher().executorService().shutdownNow();
  }
}
