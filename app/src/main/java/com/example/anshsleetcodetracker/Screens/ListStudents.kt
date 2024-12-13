package com.example.anshsleetcodetracker.Screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.anshsleetcodetracker.R
import com.example.anshsleetcodetracker.ViewModel.AttemptStatus
import com.example.anshsleetcodetracker.ViewModel.UserViewModel
import com.example.anshsleetcodetracker.ui.theme.User
import kotlinx.coroutines.launch

@Composable
fun UserItem(user: User, userViewModel: UserViewModel) {
    // Local state to track the attempt status for this specific user
    val attemptStatus = userViewModel.attemptStatuses.value[user.username]

    val done = when (attemptStatus) {
        is AttemptStatus.Loading -> null // Show loading state
        is AttemptStatus.Success -> !(attemptStatus as AttemptStatus.Success).hasAttempted // Update with result
        is AttemptStatus.Error -> null // Error, reset the state
        else -> null
    }

    Row(
        modifier = Modifier.padding(8.dp)
    ) {
        Text(text = user.username)
        Spacer(modifier = Modifier.padding(30.dp))

        Button(
            onClick = {
                userViewModel.fetchUsers()
                userViewModel.saveUser(username = user.username)
                userViewModel.checkIfUserAttemptedToday(user.username)
            }
        ) {
            Image(
                painter = painterResource(
                    id = if (done == null) {
                        R.drawable.baseline_refresh_24
                    } else if (done == false)
                        R.drawable.baseline_check_24
                    else
                        R.drawable.baseline_block_24
                ), contentDescription = ""
            )
        }
    }
}


@Composable
fun ListStudents(username: String, userViewModel: UserViewModel = UserViewModel()) {
    val users by userViewModel.users.observeAsState(emptyList())

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        userViewModel.fetchUsers()
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 30.dp)
            ) {
                items(users) { user ->
                    UserItem(user = user, userViewModel)
                }
            }
        }
    }
}


