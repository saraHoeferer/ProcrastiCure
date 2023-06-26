package com.example.procrasticure.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.procrasticure.data.State
import com.example.procrasticure.data.repository.UserRepository
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(State<AuthResult>())
    val uiState: StateFlow<State<AuthResult>> = _uiState

    suspend fun signIn(email: String, password: String, sessionViewModel: BigViewModel) {
        viewModelScope.launch {
            userRepository.signInUser(email, password, sessionViewModel).onEach { state ->
                _uiState.emit(state)
            }.launchIn(viewModelScope)
        }
    }

    suspend fun signUp(email: String, password: String, sessionViewModel: BigViewModel) {
        viewModelScope.launch {
            userRepository.signUpUser(email, password, sessionViewModel).onEach { state ->
                _uiState.emit(state)
            }.launchIn(viewModelScope)
        }
    }

    suspend fun delete(sessionViewModel: BigViewModel) {
        viewModelScope.launch {
            userRepository.deleteUser(sessionViewModel = sessionViewModel)
            resetUiState()
        }
    }

    suspend fun editEmail(email: String, sessionViewModel: BigViewModel, context: Context) {
        viewModelScope.launch {
            userRepository.editEmail(email, sessionViewModel, context)
            resetUiState()
        }
    }

    suspend fun editPassword(password: String, sessionViewModel: BigViewModel, context: Context) {
        viewModelScope.launch {
            userRepository.editPassword(password, sessionViewModel, context)
            resetUiState()
        }
    }

    suspend fun logout(sessionViewModel: BigViewModel) {
        viewModelScope.launch {
            userRepository.logOut(sessionViewModel)
            resetUiState()
        }
    }

    suspend fun givePointstoUser(sessionViewModel: BigViewModel, points: Long) {
        viewModelScope.launch {
            userRepository.givePointsToUser(sessionViewModel, points)
            resetUiState()
        }
    }

    fun resetUiState() {
        viewModelScope.launch {
            _uiState.emit(State())
        }
    }


}