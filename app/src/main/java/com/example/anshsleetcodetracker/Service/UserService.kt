package com.example.anshsleetcodetracker.Service

import com.example.anshsleetcodetracker.Model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {

    @GET("/new-user/{language}/{username}/{password}")
    suspend fun saveUser(
        @Path("language") language: String,
        @Path("username") username: String,
        @Path("password") password: String
    ): Response<User> // Response will allow checking the status code and the body


//    @GET("/today/{username}")
//    suspend fun checkUser(
//        @Path("username") username: String
//    ): Boolean // Boolean for status, assuming this is the body of ResponseEntity
//
//    @GET("users")
//    suspend fun getUsers(): List<User> // List<User> for users
//
//    @POST("/delete/{username}")
//    suspend fun deleteUser(
//        @Path("username") username: String
//    ) // No response body, assuming we just check status code

    @GET("/login/{username}/{password}")
    suspend fun loginUser(
        @Path("username") username: String,
        @Path("password") password: String
    ): Response<Boolean> // Boolean response indicating login success
}
