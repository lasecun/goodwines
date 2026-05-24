package com.lasecun.goodwines.features.auth.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.lasecun.goodwines.core.presentation.components.GoodwinesButton
import com.lasecun.goodwines.features.auth.presentation.viewmodel.AuthViewModel
import com.lasecun.goodwines.features.auth.presentation.viewmodel.RegisterFormState
import org.koin.compose.koinInject

@Composable
fun RegisterScreen(
    onNavigateToLogin: () -> Unit,
    viewModel: AuthViewModel = koinInject()
) {
    val formState by viewModel.registerForm.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var displayName by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 32.dp, vertical = 48.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Create Account",
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = "Start your wine journey",
                style = MaterialTheme.typography.bodyMedium
            )

            OutlinedTextField(
                value = displayName,
                onValueChange = {
                    displayName = it
                    if (formState is RegisterFormState.Error) viewModel.clearRegisterError()
                },
                label = { Text("Display Name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )
            )

            OutlinedTextField(
                value = username,
                onValueChange = {
                    username = it
                    if (formState is RegisterFormState.Error) viewModel.clearRegisterError()
                },
                label = { Text("Username") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )
            )

            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    if (formState is RegisterFormState.Error) viewModel.clearRegisterError()
                },
                label = { Text("Email") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                ),
                isError = formState is RegisterFormState.Error
            )

            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    if (formState is RegisterFormState.Error) viewModel.clearRegisterError()
                },
                label = { Text("Password (min. 8 characters)") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        viewModel.register(email, password, username, displayName)
                    }
                ),
                isError = formState is RegisterFormState.Error
            )

            if (formState is RegisterFormState.Error) {
                Text(
                    text = (formState as RegisterFormState.Error).message,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            GoodwinesButton(
                text = "Create Account",
                onClick = { viewModel.register(email, password, username, displayName) },
                isLoading = formState is RegisterFormState.Loading,
                modifier = Modifier.fillMaxWidth()
            )

            TextButton(onClick = onNavigateToLogin) {
                Text("Already have an account? Sign in")
            }
        }
    }
}
