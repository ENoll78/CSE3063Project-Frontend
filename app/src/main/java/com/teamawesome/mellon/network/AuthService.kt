// network/AuthService.kt
package com.teamawesome.mellon.network

import com.teamawesome.mellon.models.LoginRequest
import com.teamawesome.mellon.models.LoginResponse
import retrofit2.Response
import retrofit2.http.*

interface AuthService {
    @POST("/api/auth/login")
    suspend fun login(@Body req: LoginRequest): Response<LoginResponse>

    @GET("/api/admins/username/{username}")
    suspend fun checkUsername(@Path("username") username: String): Response<Unit>
}
