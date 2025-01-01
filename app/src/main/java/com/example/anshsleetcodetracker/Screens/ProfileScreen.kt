package com.example.anshsleetcodetracker.Screens

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.collectAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.anshsleetcodetracker.Components.CommonDialog
import com.example.anshsleetcodetracker.Helper.ResultState
import com.example.anshsleetcodetracker.Model.User
import com.example.anshsleetcodetracker.SharedPreferences.SharedPreferencesManager
import com.example.anshsleetcodetracker.ViewModel.UserViewModel

@Composable
fun ProfileScreen(viewModel: UserViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val sharedPreferences = SharedPreferencesManager
    val username = sharedPreferences.getUsername(context) ?: "" // Get username from SharedPreferences

    // Observe the userState LiveData directly
    val userState by viewModel.UserState.observeAsState(ResultState.Idle)

    // Handle the different states for userState
    when (val state = userState) {
        is ResultState.Idle -> {
            Text(
                text = "Fetching user data...",
                color = Color.Gray,
                fontSize = 18.sp
            )
        }
        is ResultState.Loading -> {
            CommonDialog() // Show the loading dialog
        }
        is ResultState.Success -> {
            val user = state.data
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Welcome, ${user.username}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Language: ${user.language}",
                    fontSize = 18.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        is ResultState.Failure -> {
            Text(
                text = "Error: ${state.msg}",
                color = Color.Red,
                fontSize = 18.sp
            )
        }
    }

    // Trigger user fetch request when the screen is displayed
    LaunchedEffect(username) {
        if (username.isNotEmpty()) {
            viewModel.fetchUser(username) // Fetch user data when the screen is displayed
        }
    }
}
