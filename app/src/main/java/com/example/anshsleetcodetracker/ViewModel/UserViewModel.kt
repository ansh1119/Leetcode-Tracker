package com.example.anshsleetcodetracker.ViewModel

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anshsleetcodetracker.Repository.UserRepository
import com.example.anshsleetcodetracker.ResultState
import com.example.anshsleetcodetracker.ui.theme.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.io.IOException

@SuppressLint("StaticFieldLeak")
class UserViewModel : ViewModel() {

    private val userRepository = UserRepository()

    // Use a map to hold each user's attempt status
    private val _attemptStatuses = mutableStateOf<Map<String, AttemptStatus>>(emptyMap())
    val attemptStatuses: State<Map<String, AttemptStatus>> = _attemptStatuses


    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> get() = _users

    private val _saveUserState = MutableStateFlow<ResultState<String>>(ResultState.Idle)
    val saveUserState: StateFlow<ResultState<String>> = _saveUserState


    fun saveUser(username: String) {
        viewModelScope.launch {
            _saveUserState.value = ResultState.Loading // Set to loading before the process starts
            try {
                // Call the repository's saveUser function and collect the result
                val user=userRepository.saveUser(username)
                val status=user.userDetails.status
                Log.d("status",user.userDetails.status)
                if(status=="error"){
                    userRepository.deleteUser(username)
                    _saveUserState.value=ResultState.Failure("wrong username")
                    Log.d("VIEWMODEL",_saveUserState.value.toString())
                    Log.d("VIEWMODEL", saveUserState.value.toString())
                }
                else{
                    _saveUserState.value=ResultState.Success("user saved")
                    Log.d("VIEWMODEL",_saveUserState.value.toString())
                    Log.d("VIEWMODEL", saveUserState.value.toString())
                }
            } catch (e: Exception) {
                // Handle unexpected exceptions and return a failure
                ResultState.Failure(e.message ?: "Unknown error")
                Log.d("VIEWMODEL",_saveUserState.value.toString())
                Log.d("VIEWMODEL",_saveUserState.value.toString())
            }
        }
    }





    fun checkIfUserAttemptedToday(username: String) {
        viewModelScope.launch {
            // Update the specific user's status to Loading
            _attemptStatuses.value = _attemptStatuses.value + (username to AttemptStatus.Loading)

            try {
                val hasAttempted = userRepository.hasUserAttemptedToday(username)
                // Update the specific user's status to Success
                _attemptStatuses.value = _attemptStatuses.value + (username to AttemptStatus.Success(hasAttempted))
            } catch (e: Exception) {
                // Update the specific user's status to Error
                _attemptStatuses.value = _attemptStatuses.value + (username to AttemptStatus.Error(e))
            }
        }
    }


    fun fetchUsers() {
        viewModelScope.launch {
            try {
                val usersList = userRepository.getUsers()
                _users.value = usersList // Set the fetched list of users
            } catch (e: Exception) {
                // Handle exceptions (e.g., network errors)
            }
        }
    }

    fun deleteUser(username: String){
        viewModelScope.launch {
            try {
                userRepository.deleteUser(username)
            } catch (e:Exception){
                Log.d("DELETE ERROR",e.toString())
            }
        }

    }

}


// Define the status states
sealed class SaveUserStatus {
    object Idle : SaveUserStatus()
    object Loading : SaveUserStatus()
    object Success : SaveUserStatus()
    data class Error(val exception: Exception) : SaveUserStatus()
}

// Sealed class to represent different states of the attempt status
sealed class AttemptStatus {
    object Idle : AttemptStatus()
    object Loading : AttemptStatus()
    data class Success(val hasAttempted: Boolean) : AttemptStatus()
    data class Error(val exception: Exception) : AttemptStatus()
}