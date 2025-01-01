package com.example.anshsleetcodetracker.Screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.anshsleetcodetracker.Components.AuthTextField
import com.example.anshsleetcodetracker.Components.CommonDialog
import com.example.anshsleetcodetracker.Helper.ResultState
import com.example.anshsleetcodetracker.Model.User
import com.example.anshsleetcodetracker.R
import com.example.anshsleetcodetracker.SharedPreferences.SharedPreferencesManager
import com.example.anshsleetcodetracker.ViewModel.UserViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    userViewModel: UserViewModel = hiltViewModel()
) {
    var isDialog by remember { mutableStateOf(false) }
    val userState by userViewModel.userState
    val loginResult by userViewModel.loginResult.collectAsState() // Observe login result
    val context = LocalContext.current
    val sharedPreferences = SharedPreferencesManager

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Show loading dialog while isDialog is true
    if (isDialog) {
        CommonDialog() // Show loading dialog
    }

    // Handle the result of login and fetch user
    when (val state = userState) {
        is ResultState.Idle -> {
            isDialog = false
        }
        is ResultState.Loading -> {
            isDialog = true
        }
        is ResultState.Success -> {
            // Fetch the user after successful login
            val user = state.data
            sharedPreferences.setLanguage(context, user.language)
            sharedPreferences.setUsername(context, user.username)

            // Hide dialog after setting preferences
            isDialog = false
            // Perform navigation only after user is logged in and preferences are set
            navController.navigate("wait/${user.language}")
        }
        is ResultState.Failure -> {
            isDialog = false // Hide dialog on failure
            val errorMessage = state.msg ?: "Unknown error"
            Toast.makeText(context, "Error: $errorMessage", Toast.LENGTH_LONG).show()
        }
    }

    // Handle login result and show appropriate message
    loginResult?.let { result ->
        when {
            result.isSuccess -> {
                // If login is successful, fetch the user data
                userViewModel.fetchUserForLoginScreen(username)
            }
            result.isFailure -> {
                Toast.makeText(context, "Login Failed: ${result.exceptionOrNull()?.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    // The main UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF111112)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "",
            modifier = Modifier.size(141.dp)
        )

        Text(
            text = "Welcome Back!",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )
        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = "Please log in or create an account",
            fontSize = 14.sp,
            color = Color.White
        )

        // Username input field
        AuthTextField(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(top = 20.dp),
            label = "Username",
            value = username,
            placeHolder = "Username"
        ) { newValue -> username = newValue }

        // Password input field
        AuthTextField(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(top = 12.dp),
            label = "Password",
            value = password,
            placeHolder = "MyPassword"
        ) { newValue -> password = newValue }

        // Continue button
        Button(
            onClick = {
                if (username.isEmpty()) {
                    Toast.makeText(context, "ENTER USERNAME", Toast.LENGTH_LONG).show()
                } else if (password.isEmpty()) {
                    Toast.makeText(context, "ENTER PASSWORD", Toast.LENGTH_LONG).show()
                } else {
                    // Perform login operation
                    userViewModel.loginUser(username, password)
                }
            },
            modifier = Modifier
                .padding(top = 40.dp)
                .fillMaxWidth(0.9f)
                .height(61.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF49E1C7))
        ) {
            Text(text = "Continue", color = Color(0xFF111112))
        }

        // Register account button
        Button(
            onClick = {
                navController.navigate("register")
            },
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth(0.9f)
                .height(61.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF171919))
        ) {
            Text(text = "Register account")
        }
    }
}
