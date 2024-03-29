package com.mobile.restclient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitInterface {
    @GET("/posts")
    Call<List<Post>> getPosts();

    @GET("/users")
    Call<List<User>> getAllUsers();

    @GET("/users/{userId}")
    Call<User> getUser(@Path("userId") int userId);

    @GET("/comments")
    Call<List<Comment>> getPostComments(@Query("postId") int pId);

    @GET("/posts")
    Call<List<Post>> getUserPosts(@Query("userId") int uId);

    @POST("/comments")
    Call<Comment> createComment(@Body Comment comment);
}
