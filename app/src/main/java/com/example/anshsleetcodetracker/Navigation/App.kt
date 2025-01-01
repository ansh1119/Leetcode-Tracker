package com.example.anshsleetcodetracker

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.anshsleetcodetracker.Screens.LoginScreen
import com.example.anshsleetcodetracker.Screens.ProfileScreen
import com.example.anshsleetcodetracker.Screens.RegisterScreen
import com.example.anshsleetcodetracker.Screens.StreakScreen
import com.example.anshsleetcodetracker.Screens.WaitingScreen
//import com.example.anshsleetcodetracker.Screens.WaitingScreen
import com.example.anshsleetcodetracker.SharedPreferences.SharedPreferencesManager
import com.example.anshsleetcodetracker.ViewModel.UserViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun App() {

    val viewModel:UserViewModel= hiltViewModel()
    val sharedPreferences=SharedPreferencesManager
    val navController = rememberNavController()
    val context = LocalContext.current

    if(sharedPreferences.getLanguage(context)!=null){
        viewModel.fetchStreaks(sharedPreferences.getLanguage(context)!!)
    }

    NavHost(
        navController = navController,
        startDestination = if(sharedPreferences.getUsername(context)!=null){
            "streak"
        }
        else{
            "login"
        }
    ) {
        composable(route = "login") {
            LoginScreen(navController)
        }
        composable(
            route = "register"
        ) {
            RegisterScreen(navController, userViewModel = viewModel)
        }
        composable(route="wait/{language}",
            arguments = listOf(navArgument("language") { type = NavType.StringType })){backStackEntry->
            val language = backStackEntry.arguments?.getString("language") ?: ""
            WaitingScreen(navController = navController, language, viewModel)
        }
        composable(route="streak") {
            StreakScreen(navController,viewModel)
        }
        composable(route="profile") {
            ProfileScreen()
        }
    }

}