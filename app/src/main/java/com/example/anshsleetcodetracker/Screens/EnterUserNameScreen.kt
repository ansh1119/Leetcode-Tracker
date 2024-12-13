package com.example.anshsleetcodetracker.Screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.anshsleetcodetracker.Repository.UserRepository
import com.example.anshsleetcodetracker.ResultState
import com.example.anshsleetcodetracker.SharedPreferencesManager
import com.example.anshsleetcodetracker.ViewModel.SaveUserStatus
import com.example.anshsleetcodetracker.ViewModel.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


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
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            TextField(value = username, onValueChange = { username = it })
        }
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
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "ENTER")
        }


    }
}


@Composable
fun CommonDialog() {

    Dialog(onDismissRequest = { }) {
        CircularProgressIndicator()
    }

}