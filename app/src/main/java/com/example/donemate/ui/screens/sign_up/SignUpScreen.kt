package com.example.donemate.ui.screens.sign_up

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay

@Composable
fun SignUpScreen(navigateToSignIn: () -> Unit, vm: SignUpViewModel){
    val uiState by vm.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        vm.onAppStart()
    }

    Row(modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center) {
        Column(
            modifier = Modifier.width(300.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Sign Up",
                fontSize = 35.sp
            )
            Spacer(Modifier.height(15.dp))

            TextField(
                value = uiState.email,
                onValueChange = vm::onEmailChange,
                label = { Text("E-mail") }
            )
            Spacer(Modifier.height(8.dp))

            TextField(
                value = uiState.password,
                onValueChange = vm::onPasswordChange,
                label = { Text("Password") }
            )
            Spacer(Modifier.height(8.dp))

            Button(
                onClick = {vm.onSignUpClick()},
                modifier = Modifier.width(283.dp)
            ) {
                Text("Sign up")
            }
            Spacer(Modifier.height(8.dp))
            Text(text="Go to sign in",modifier = Modifier.clickable{
                navigateToSignIn()
            })
        }
    }
}