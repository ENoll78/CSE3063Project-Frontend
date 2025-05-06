// MainActivity.kt
package com.teamawesome.mellon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.teamawesome.mellon.network.AuthService
import com.teamawesome.mellon.repository.AuthRepository
import com.teamawesome.mellon.ui.LoginScreen
import com.teamawesome.mellon.ui.SuccessScreen
import com.teamawesome.mellon.ui.theme.MellonTheme
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import okhttp3.OkHttpClient

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val client = OkHttpClient.Builder().build()
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .client(client)
            .addConverterFactory(JacksonConverterFactory.create())
            .build()

        val authService    = retrofit.create(AuthService::class.java)
        val authRepository = AuthRepository(authService)

        setContent {
            MellonTheme {
                var successMessage by remember { mutableStateOf<String?>(null) }

                if (successMessage == null) {
                    LoginScreen(authRepository) { msg ->
                        successMessage = msg
                    }
                } else {
                    SuccessScreen(successMessage!!)
                }
            }
        }
    }
}
