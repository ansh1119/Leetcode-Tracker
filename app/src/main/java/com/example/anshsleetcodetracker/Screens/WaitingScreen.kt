package com.example.anshsleetcodetracker.Screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.anshsleetcodetracker.ViewModel.UserViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WaitingScreen(navController: NavController, language: String, viewModel: UserViewModel ) {

    var show by remember { mutableStateOf(true) }
    var hasNavigated by remember { mutableStateOf(false) }  // Track if the navigation has already happened

    LaunchedEffect(key1 = Unit) {
        viewModel.fetchStreaks(language)
        kotlinx.coroutines.delay(3000) // 2 second delay
        show = false
        if (!hasNavigated) {
            hasNavigated = true
            navController.navigate("streak")
        }
    }

    val currentTime = remember { LocalDateTime.now() }

    // Extract the current hour and minute
    val currentHour = currentTime.hour
    val currentMinute = currentTime.minute

    val timeRangeMessage = when {
        currentHour == 8 && currentMinute >= 0 || currentHour in 9..11 || (currentHour == 12 && currentMinute == 0) -> "Hey, Good Morning!\n Attendance kaisi chal rahi?"
        currentHour == 12 && currentMinute > 0 || currentHour in 13..14 -> "Do not forget your lunch...Bunk kar rhe next class?"
        currentHour == 15 && currentMinute > 0 || currentHour in 16..17 -> "bas bas almost khatam ho gya college"
        currentHour == 17 && currentMinute > 0 || currentHour in 18..19 -> "club nhi h kya aaj?"
        currentHour == 19 && currentMinute > 0 || currentHour in 20..23 || (currentHour == 0 && currentMinute == 0) -> "badhiya badhiya padho acche se"
        currentHour == 0 && currentMinute > 0 || currentHour in 1..4 -> "so bhi liya karo baad m bologe ki raat bhar nahi soye,\n it's not an achievement yk"
        currentHour == 5 && currentMinute > 0 || currentHour in 6..7 || (currentHour == 8 && currentMinute == 0) -> "Good Morning early bird,\n waah...aaj sher jaldi utha hai"
        else -> "Unknown Time Range"
    }

    if (show) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF1E1E1E)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(color = Color.White)
            Text(text = timeRangeMessage, color = Color.White)
        }
    }
}
