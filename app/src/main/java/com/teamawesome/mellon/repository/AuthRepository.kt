// repository/AuthRepository.kt
package com.teamawesome.mellon.repository

import com.teamawesome.mellon.models.LoginRequest
import com.teamawesome.mellon.models.LoginResponse
import com.teamawesome.mellon.network.AuthService
import retrofit2.Response

class AuthRepository(private val authService: AuthService) {

    suspend fun login(usernameOrEmail: String, password: String): Response<LoginResponse> {
        return authService.login(LoginRequest(usernameOrEmail, password))
    }

    suspend fun isUsernameAvailable(username: String): Boolean {
        val resp = authService.checkUsername(username)
        return resp.code() == 404
    }
}
