// ui/LoginScreen.kt
package com.teamawesome.mellon.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.teamawesome.mellon.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun LoginScreen(
    authRepo: AuthRepository,
    onLoginSuccess: (String) -> Unit
) {
    var userOrEmail by rememberSaveable { mutableStateOf("") }
    var password    by rememberSaveable { mutableStateOf("") }
    var message     by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = userOrEmail,
            onValueChange = { userOrEmail = it },
            label = { Text("Username or Email") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = {
                message = null
                scope.launch {
                    val resp = authRepo.login(userOrEmail.trim(), password)
                    withContext(Dispatchers.Main) {
                        if (resp.isSuccessful) {
                            val body = resp.body()!!
                            onLoginSuccess(body.message)
                        } else {
                            message = resp.errorBody()?.string() ?: "Login failed"
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
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}
