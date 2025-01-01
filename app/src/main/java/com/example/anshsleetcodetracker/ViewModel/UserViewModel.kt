package com.example.anshsleetcodetracker.ViewModel

import android.annotation.SuppressLint
import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anshsleetcodetracker.Repository.UserRepository
import com.example.anshsleetcodetracker.Helper.ResultState
import com.example.anshsleetcodetracker.Model.User
import com.example.anshsleetcodetracker.RetrofitInstance
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {


    // Use a map to hold each user's attempt status
//    private val _attemptStatuses = mutableStateOf<Map<String, AttemptStatus>>(emptyMap())
//    val attemptStatuses: State<Map<String, AttemptStatus>> = _attemptStatuses

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    private val _loginResult = MutableStateFlow<Result<Boolean>?>(null)
    val loginResult: StateFlow<Result<Boolean>?> get() = _loginResult

//
//    private val _users = MutableLiveData<List<User>>()
//    val users: LiveData<List<User>> get() = _users

    private val _saveUserState = MutableStateFlow<ResultState<String>>(ResultState.Idle)
    val saveUserState: StateFlow<ResultState<String>> = _saveUserState


    fun saveUser(language: String, username: String, password: String) {
        viewModelScope.launch {
            _saveUserState.value = ResultState.Loading // Set to loading before the process starts

            try {
                // Call the repository's saveUser function and collect the result
                val user = userRepository.saveUser(language, username, password)
                _saveUserState.value = ResultState.Success("User saved successfully")
                Log.d("VIEWMODEL", _saveUserState.value.toString())
            } catch (e: Exception) {
                // Handle any errors (e.g., user not found, invalid username)
                _saveUserState.value = ResultState.Failure(e.message ?: "Unknown error")
                Log.d("VIEWMODEL", e.toString())
            }
        }
    }


    fun loginUser(username: String, password: String) {
        viewModelScope.launch {
            _isLoading.value = true // Set loading state to true

            try {
                val isSuccess = userRepository.login(username, password)

                if (isSuccess) {
                    _loginResult.value = Result.success(isSuccess) // Emit success result
                } else {
                    // Emit a failure result with an error message indicating invalid credentials
                    _loginResult.value =
                        Result.failure(Exception("Invalid credentials. Please try again."))
                }
            } catch (e: Exception) {
                _loginResult.value = Result.failure(e) // Emit the error if login fails
            } finally {
                _isLoading.value = false // Set loading state to false
            }
        }
    }


    private val _userState = mutableStateOf<ResultState<User>>(ResultState.Idle)
    val userState: State<ResultState<User>> get() = _userState

    fun fetchUserForLoginScreen(username: String) {
        _userState.value = ResultState.Loading

        viewModelScope.launch {
            try {
                val user = userRepository.getUser(username)
                _userState.value = ResultState.Success(user)
            } catch (e: Exception) {
                _userState.value = ResultState.Failure(e.message ?: "Unknown error")
            }
        }
    }

    private val _UserState = MutableLiveData<ResultState<User>>(ResultState.Idle)
    val UserState: LiveData<ResultState<User>> get() = _UserState

    fun fetchUser(username: String) {
        _UserState.value = ResultState.Loading

        viewModelScope.launch {
            try {
                val user = userRepository.getUser(username)
                _UserState.value = ResultState.Success(user)
            } catch (e: Exception) {
                _UserState.value = ResultState.Failure(e.message ?: "Unknown error")
            }
        }
    }


    private val _streaks = MutableStateFlow<Map<String, List<Boolean>>?>(null)
    val streaks: StateFlow<Map<String, List<Boolean>>?> = _streaks

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun fetchStreaks(language: String) {
        viewModelScope.launch {
            try {
                val result = userRepository.getStreakOfUsers(language)
                _streaks.value = result
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }
}