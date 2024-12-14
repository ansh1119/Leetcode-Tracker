package com.example.anshsleetcodetracker.ui.theme

import com.example.anshsleetcodetracker.UserDetails
//import kotlinx.serialization.Serializable


//@Serializable
data class User(
    val username:String,
    val userDetails: UserDetails
)

data class UserDetails(
    val name: String,
    val email: String
)
