package com.example.anshsleetcodetracker.Repository

import android.util.Log
import com.example.anshsleetcodetracker.ResultState
import com.example.anshsleetcodetracker.Retrofit.RetrofitInstance
import com.example.anshsleetcodetracker.UserService
import com.example.anshsleetcodetracker.ui.theme.User

class UserRepository {

    private val userService: UserService = RetrofitInstance.providesPublicRetrofit()
        .create(UserService::class.java)

    // Function to save user details
    suspend fun saveUser(username: String): User {
        // Call the service to save the user
        val user = userService.saveUser(username)
        return user
    }


    // Function to check if the user attempted today
    suspend fun hasUserAttemptedToday(username: String): Boolean {
        return userService.checkUser(username)
    }

    suspend fun getUsers(): List<User> {
        return userService.getUsers()
    }

    suspend fun deleteUser(username: String) {
        userService.deleteUser(username)
    }
}
