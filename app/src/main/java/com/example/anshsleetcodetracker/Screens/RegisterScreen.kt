package com.example.anshsleetcodetracker.Screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.anshsleetcodetracker.Components.AuthTextField
import com.example.anshsleetcodetracker.Components.CommonDialog
import com.example.anshsleetcodetracker.Components.CustomDropdownMenu
import com.example.anshsleetcodetracker.Helper.ResultState
import com.example.anshsleetcodetracker.R
import com.example.anshsleetcodetracker.SharedPreferences.SharedPreferencesManager
import com.example.anshsleetcodetracker.ViewModel.UserViewModel


@Composable
fun RegisterScreen(
    navController: NavController,
    userViewModel: UserViewModel
) {
    var isDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var language by remember { mutableStateOf("") }
    val sharedPreferences=SharedPreferencesManager

    val saveUserState by userViewModel.saveUserState.collectAsState()

    // Handle one-time events for navigation and Toasts
    LaunchedEffect(saveUserState) {
        when (saveUserState) {
            is ResultState.Success -> {
                isDialog = false
                sharedPreferences.setLanguage(context,language)
                sharedPreferences.setUsername(context,username)
                Toast.makeText(context, "Register Success", Toast.LENGTH_LONG).show()
                navController.navigate("wait/$language") // Navigate to waiting screen
            }

            is ResultState.Failure -> {
                isDialog = false
                val errorMessage = (saveUserState as ResultState.Failure)
                Toast.makeText(context, "Register Failed: $errorMessage", Toast.LENGTH_LONG).show()
            }

            is ResultState.Loading -> {
                isDialog = true // Show loading indicator
            }

            ResultState.Idle -> {
                isDialog = false
            }

            else -> {
                isDialog = false
            }
        }
    }


    if (isDialog) {
        CommonDialog() // Show loading dialog
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding()
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
            AuthTextField(
                modifier = Modifier
                    .fillMaxWidth(.9f)
                    .padding(top = 20.dp),
                "Username",
                username,
                "Username"
            ) { newValue ->
                username = newValue
            }
            AuthTextField(
                modifier = Modifier
                    .fillMaxWidth(.9f)
                    .padding(top = 12.dp),
                "Password",
                password,
                "MyPassword"
            ) { newValue ->
                password = newValue
            }

            CustomDropdownMenu(
                modifier = Modifier.fillMaxWidth(.9f),
                options = listOf("java", "cpp")
            ) { selectedOption ->
                language = selectedOption
            }

            Button(
                onClick = {
                    when {
                        username.isEmpty() -> {
                            Toast.makeText(context, "ENTER USERNAME", Toast.LENGTH_SHORT).show()
                        }
                        password.isEmpty() -> {
                            Toast.makeText(context, "ENTER PASSWORD", Toast.LENGTH_SHORT).show()
                        }
                        language.isEmpty() -> {
                            Toast.makeText(context, "ENTER LANGUAGE", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            userViewModel.saveUser(language, username, password)
                        }
                    }
                },
                modifier = Modifier
                    .padding(top = 40.dp)
                    .fillMaxWidth(.9f)
                    .height(61.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF49E1C7)
                )
            ) {
                Text(
                    text = "Continue",
                    color = Color(0xFF111112)
                )
            }

            Button(
                onClick = {
                    navController.navigate("login")
                },
                modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxWidth(.9f)
                    .height(61.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF171919)
                )
            ) {
                Text(text = "Login")
            }
        }
    }
}
