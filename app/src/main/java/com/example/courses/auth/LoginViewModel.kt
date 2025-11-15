package com.example.courses.auth

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEmailChanged(value: String) {
        val isValid = EMAIL_REGEX.matches(value)
        _uiState.update { old ->
            val newState = old.copy(email = value, isEmailValid = isValid)
            newState.copy(isLoginEnabled = canLogin(newState))
        }
    }

    fun onPasswordChanged(value: String) {
        _uiState.update { old ->
            val newState = old.copy(password = value)
            newState.copy(isLoginEnabled = canLogin(newState))
        }
    }

    private fun canLogin(state: LoginUiState): Boolean =
        state.email.isNotBlank() && state.password.isNotBlank() && state.isEmailValid

    companion object {

        private val EMAIL_REGEX =
            Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    }
}
