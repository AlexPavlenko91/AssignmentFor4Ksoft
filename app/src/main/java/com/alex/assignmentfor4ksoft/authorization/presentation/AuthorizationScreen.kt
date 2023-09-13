package com.alex.assignmentfor4ksoft.authorization.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.alex.assignmentfor4ksoft.R
import com.alex.assignmentfor4ksoft.utils.Screen
import com.alex.assignmentfor4ksoft.utils.UiEvent


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthorizationScreen(
    navController: NavController,
    viewModel: AuthorizationViewModel = hiltViewModel()
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val state = viewModel.state
        val context = LocalContext.current
        val snackbarHostState = remember { SnackbarHostState() }
        LaunchedEffect(key1 = context) {
            viewModel.validationEvents.collect { event ->
                when (event) {
                    is UiEvent.Success -> {
                        navController.navigate(Screen.PostsScreen.route)
                    }
                    is UiEvent.ShowSnackbar -> {
                        snackbarHostState.showSnackbar(
                            message = event.message.asString(context)
                        )
                    }
                    else -> Unit
                }

            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = state.email,
                onValueChange = {
                    viewModel.onEvent(AuthorizationEvent.EmailChanged(it))
                },
                isError = state.emailError != null,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = stringResource(R.string.email))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email
                )
            )
            if (state.emailError != null) {
                Text(
                    text = state.emailError.asString(),
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.End)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = state.password,
                onValueChange = {
                    viewModel.onEvent(AuthorizationEvent.PasswordChanged(it))
                },
                isError = state.passwordError != null,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = stringResource(R.string.password))
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation = PasswordVisualTransformation()
            )
            if (state.passwordError != null) {
                Text(
                    text = state.passwordError.asString(),
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.End)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))


            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = state.shouldRemember,
                    onCheckedChange = {
                        viewModel.onEvent(AuthorizationEvent.RememberUser(it))
                    },
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = stringResource(R.string.remember_user))
            }

            Button(
                onClick = { viewModel.onEvent(AuthorizationEvent.Submit) },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(text = stringResource(R.string.submit))
            }
        }
    }
}