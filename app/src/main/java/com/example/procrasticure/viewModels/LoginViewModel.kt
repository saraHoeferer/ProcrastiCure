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

class LoginViewModel @Inject constructor(private val userRespository: UserRespository) : ViewModel() {

    private val _uiState = MutableStateFlow(State<AuthResult>())
    val uiState: StateFlow<State<AuthResult>> = _uiState

     suspend fun  signUp(email: String, password: String) {
        viewModelScope.launch {
            userRespository.signUpUser(email, password).onEach { state ->
                _uiState.emit(state)
            }.launchIn(viewModelScope)
        }
    }

    fun resetUiState(){
        viewModelScope.launch{
            _uiState.emit(State())
        }
    }


}