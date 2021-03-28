package com.nacho.blog.springalternatives.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonPlaceholderPostService {

  @GET("posts/{postId}")
  Call<Post> getPostById(@Path("postId") Integer postId);

  @GET("posts/")
  Call<List<Post>> getPosts();

  @POST("posts/")
  Call<Post> createPost(@Body Post post);

  @GET("posts")
  Call<List<Post>> getPostsForUser(@Query("userId") Integer userId);
}
