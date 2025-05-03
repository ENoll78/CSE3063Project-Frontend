// network/AuthService.kt
package com.teamawesome.mellon.network

import com.teamawesome.mellon.models.LoginRequest
import com.teamawesome.mellon.models.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("/api/auth/login")
    suspend fun login(@Body req: LoginRequest): Response<LoginResponse>
}
