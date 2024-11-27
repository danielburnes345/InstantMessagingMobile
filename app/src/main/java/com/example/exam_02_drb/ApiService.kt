package com.example.exam_02_drb

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("users")
    fun getUsers(): Call<List<User>>
    @GET("posts")
    fun getPosts(): Call<List<Post>>
    @GET("comments")
    fun getComments() : Call<List<Comment>>
}
