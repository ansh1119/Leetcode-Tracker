package com.example.anshsleetcodetracker.Screens

import android.util.Log
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.anshsleetcodetracker.R
import com.example.anshsleetcodetracker.Repository.UserRepository
import com.example.anshsleetcodetracker.SharedPreferencesManager
import com.example.anshsleetcodetracker.ViewModel.UserViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnterUserNameScreen(
    navController: NavController,
    userViewModel: UserViewModel = UserViewModel()
) {

    val userRepository = UserRepository()

    var isDialog by remember {
        mutableStateOf(false)
    }

    if (isDialog) {
        CommonDialog()
    }

    val context = LocalContext.current;



    var username by remember {
        mutableStateOf("")
    }

    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp), // Add padding for better UI appearance
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "LeetCode Tracker",
            style = TextStyle(
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        // App Logo
        Image(
            painter = painterResource(id = R.drawable.leetcode),
            contentDescription = "logo",
            modifier = Modifier
                .size(120.dp)
                .padding(bottom = 16.dp) // Space below the image
        )
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Enter Username", color = Color(0xFF9C9A99)) },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(12.dp),
            textStyle = TextStyle(color = Color.White),
            colors = androidx.compose.material3.TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFE7A41F),
                unfocusedBorderColor = Color(0xFF9C9A99),
                cursorColor = Color(0xFFE7A41F),
                focusedLabelColor = Color(0xFFE7A41F),
                unfocusedLabelColor = Color(0xFF9C9A99)
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                Log.d("BUTTON", "save button clicked")
                if (username.isBlank()) {
                    Toast.makeText(context, "INVALID USERNAME", Toast.LENGTH_LONG).show()
                } else {
                    isDialog=true
                    username = username.trim()
                    Log.d("BUTTON", username)
                    scope.launch {
                        var user=userRepository.saveUser(username)
                        if(user.userDetails.status=="error"){
                            Toast.makeText(context,user.userDetails.message,Toast.LENGTH_LONG).show()
                            userRepository.deleteUser(username)
                            isDialog=false
                        }
                        else{
                            isDialog=false
                            SharedPreferencesManager.saveUsername(context,username)
                            navController.navigate("details/${username}")
                        }
                    }

                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE7A41F)
            ),
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .padding(horizontal = 16.dp)
                .height(48.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Enter",
                style = TextStyle(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            )
        }


    }
}


@Composable
fun CommonDialog() {

    Dialog(onDismissRequest = { }) {
        CircularProgressIndicator()
    }

}