package com.example.anshsleetcodetracker.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.anshsleetcodetracker.R
import com.example.anshsleetcodetracker.ViewModel.AttemptStatus
import com.example.anshsleetcodetracker.ViewModel.UserViewModel
import com.example.anshsleetcodetracker.ui.theme.User

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
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = user.username,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(1f)
        )
        Button(
            onClick = {
                userViewModel.fetchUsers()
                userViewModel.saveUser(username = user.username)
                userViewModel.checkIfUserAttemptedToday(user.username)
            }
        ) {
            if (done == null) {
                Image(
                    painter = painterResource(id = R.drawable.baseline_refresh_24),
                    contentDescription = ""
                )
            } else if (done == false) {
                Indicator(color = Color(0xFF00FF00)) // Green
            } else {
                Indicator(color = Color(0xFFFF4444)) // Red
            }
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

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
                    .padding(16.dp)
            ) {
                Text(
                    text = "JAVAAAZ",
                    color = Color(0xFFE7A41F),
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        },
        containerColor = Color.Black
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            items(users) { user ->
                UserItem(user = user, userViewModel)
                HorizontalDivider(
                    color = Color(0xFFE7A41F),
                    thickness = 2.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun Indicator(color: Color) {
    Box(
        modifier = Modifier
            .size(12.dp)
            .background(color,shape = CircleShape)
    )
}





