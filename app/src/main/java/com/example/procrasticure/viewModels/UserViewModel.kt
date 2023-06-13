package com.example.procrasticure.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.procrasticure.data.State
import com.example.procrasticure.data.repository.UserRespository
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserViewModel @Inject constructor(private val userRespository: UserRespository) : ViewModel() {

    private val _uiState = MutableStateFlow(State<AuthResult>())
    val uiState: StateFlow<State<AuthResult>> = _uiState

    suspend fun signIn(email: String, password: String, sessionViewModel: BigViewModel) {
        viewModelScope.launch {
            userRespository.signInUser(email, password, sessionViewModel).onEach { state ->
                _uiState.emit(state)
            }.launchIn(viewModelScope)
        }
    }

    suspend fun signUp(email: String, password: String, sessionViewModel: BigViewModel) {
        viewModelScope.launch {
            userRespository.signUpUser(email, password, sessionViewModel).onEach { state ->
                _uiState.emit(state)
            }.launchIn(viewModelScope)
        }
    }

    suspend fun delete(sessionViewModel: BigViewModel){
        viewModelScope.launch {
            userRespository.deleteUser(sessionViewModel = sessionViewModel)
            resetUiState()
        }
    }

    suspend fun editEmail(email: String, sessionViewModel: BigViewModel){
        viewModelScope.launch {
            userRespository.editEmail(email, sessionViewModel)
        }
    }

    suspend fun editPassword(password: String, sessionViewModel: BigViewModel){
        viewModelScope.launch {
            userRespository.editPassword(password, sessionViewModel)
        }
    }

    suspend fun logout(sessionViewModel: BigViewModel){
        viewModelScope.launch {
            userRespository.logOut(sessionViewModel)
            resetUiState()
        }
    }

    fun resetUiState(){
        viewModelScope.launch{
            _uiState.emit(State())
        }
    }


}