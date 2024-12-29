package com.example.anshsleetcodetracker

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.anshsleetcodetracker.Screens.LoginScreen
import com.example.anshsleetcodetracker.Screens.RegisterScreen

@Composable
fun App() {

    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable(route = "login") {
            LoginScreen(navController)
        }
        composable(
            route = "register"
        ) {
            RegisterScreen(navController)
        }
    }

}