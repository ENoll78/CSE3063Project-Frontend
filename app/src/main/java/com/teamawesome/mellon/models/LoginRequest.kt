package com.teamawesome.mellon.models

data class LoginRequest (
    val userNameOrEmail: String,
    val password: String
)