// ui/LoginScreen.kt
package com.teamawesome.mellon.ui

import com.teamawesome.mellon.repository.AuthRepository

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext



@Composable
fun LoginScreen(authRepo: AuthRepository) {
    var userOrEmail by rememberSaveable { mutableStateOf("") }
    var password    by rememberSaveable { mutableStateOf("") }
    var message     by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = userOrEmail,
            onValueChange = { newText: String -> userOrEmail = newText },
            label = { Text("Username or Email") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { newPassword: String -> password = newPassword },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = {
                message = null
                CoroutineScope(Dispatchers.IO).launch {
                    val resp = authRepo.login(userOrEmail.trim(), password)
                    withContext(Dispatchers.Main) {
                        message = if (resp.isSuccessful) {
                            resp.body()?.message ?: "Login succeeded"
                        } else {
                            resp.errorBody()?.string() ?: "Login failed"
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }
        message?.let {
            Spacer(Modifier.height(8.dp))
            Text(
                text = it,
                color = if (it.contains("succeed"))
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.error
            )
        }
    }
}
