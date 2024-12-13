package com.example.anshsleetcodetracker

//import kotlinx.serialization.Serializable


//@Serializable
data class UserDetails(
    val status: String,
    val message: String,
    val totalSolved: Int,
    val totalQuestions: Int,
    val easySolved: Int,
    val totalEasy: Int,
    val mediumSolved: Int,
    val totalMedium: Int,
    val hardSolved: Int,
    val totalHard: Int,
    val acceptanceRate: Double,
    val ranking: Int,
    val contributionPoints: Int,
    val reputation: Int,
    val submissionCalendar: Map<Long, Int>
)
