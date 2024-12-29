package com.example.anshsleetcodetracker.Repository


import com.example.anshsleetcodetracker.Service.UserService
import com.example.anshsleetcodetracker.Model.User
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class UserRepository @Inject constructor(
    private val userService: UserService
) {

    suspend fun saveUser(language: String, username: String, password: String): User {
        val response = userService.saveUser(language, username, password)

        if (response.isSuccessful) {
            // If the response was successful (HTTP 201)
            return response.body() ?: throw Exception("Failed to create user: No body in response")
        } else {
            // Handle error response (HTTP 400 or other)
            throw Exception("Error creating user: ${response.code()}")
        }
    }

    suspend fun login(username: String, password: String): Boolean {
        val response = userService.loginUser(username, password)

        if (response.isSuccessful) {
            // If the response is successful (HTTP 200), check the body for the actual result
            return response.body() ?: false
        } else {
            // If the response is not successful (e.g., HTTP 400), handle the error or return false
            throw Exception("Login failed: ${response.code()}")
        }
    }

}


sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    object Loading : Result<Nothing>()
}