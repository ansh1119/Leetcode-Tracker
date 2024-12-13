package com.example.anshsleetcodetracker

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.anshsleetcodetracker.Screens.EnterUserNameScreen
import com.example.anshsleetcodetracker.Screens.ListStudents

@Composable
fun App() {

    val navController = rememberNavController()
    val context = LocalContext.current

    val username=SharedPreferencesManager.getUsername(context)

    NavHost(
        navController = navController,
        startDestination =
            if (username != "username")
                "details/{$username}"
            else
                "username"
    ) {
        composable(route = "username") {
            EnterUserNameScreen(navController)
        }
        composable(
            route = "details/{username}",
            arguments = listOf(navArgument("username") { type = NavType.StringType })
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username")
            ListStudents(username = username.toString())
        }
    }

}