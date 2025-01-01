package com.example.anshsleetcodetracker.Screens

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.anshsleetcodetracker.Components.CommonDialog
import com.example.anshsleetcodetracker.ViewModel.UserViewModel

@Composable
fun StreakScreen(navController: NavController, viewModel: UserViewModel) {

    val streaks by viewModel.streaks.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    var languageInput by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(horizontal = 16.dp)
    ) {

        Spacer(modifier = Modifier.height(30.dp))

        when {
            streaks != null -> {
                LazyColumn(Modifier.padding(horizontal = 16.dp)) {
                    streaks?.forEach { (user, streak) ->
                        item {
                            StreakItem(user, streak)
                        }
                    }
                }
            }
            errorMessage != null -> {
                Text(
                    text = errorMessage ?: "An error occurred",
                    color = Color.Red,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
            else -> {
                CommonDialog()
            }
        }

        Spacer(modifier = Modifier.weight(1f)) // Push button to the bottom

//        Button(
//            onClick = { navController.navigate("profile") },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//        ) {
//            Text(text = "Profile")
//        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StreakItem(user: String, streak: List<Boolean>) {
    // Animate the size of each item for smoother content changes
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
//            .animateItemPlacement() // Enable smooth animation for item movement
    ) {
        // Display user name
        Text(
            text = "$user",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Display each streak as a colored circle (green for true, red for false)
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            streak.reversed().forEach { isActive ->
                Box(
                    modifier = Modifier
                        .size(16.dp) // Circle size
                        .background(
                            color = if (isActive) Color.Green else Color.Red,
                            shape = CircleShape
                        )
                        .animateContentSize() // Smoothly animate size changes
                )
            }
        }
    }
}
