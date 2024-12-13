package com.example.anshsleetcodetracker

import com.example.anshsleetcodetracker.ui.theme.User
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {

    @GET("/new-user/{username}")
    suspend fun saveUser(
        @Path("username") username: String
    ): User

    @GET("/today/{username}")
    suspend fun checkUser(
        @Path("username") username: String
    ):Boolean

    @GET("users")
    suspend fun getUsers():List<User>

    @POST("/delete/{username}")
    suspend fun deleteUser(
        @Path("username") username: String
    )
}