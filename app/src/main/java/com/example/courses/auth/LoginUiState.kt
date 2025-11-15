package com.example.courses.auth


data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isEmailValid: Boolean = false,
    val isLoginEnabled: Boolean = false
)
