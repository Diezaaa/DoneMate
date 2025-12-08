package com.example.donemate.ui.screens.sign_in

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SignInScreen(
    navigateToSignUp: () -> Unit,
    vm: SignInViewModel,
    navigateToSuccess: () -> Boolean
){
    val uiState by vm.uiState.collectAsStateWithLifecycle()
    val email by vm.currentEmail.collectAsStateWithLifecycle()

    Row(modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center) {
        Column(
            modifier = Modifier.width(300.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = email + "")
            Text(
                "Sign In",
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
                onClick = {vm.onSignInClick()},
                modifier = Modifier.width(283.dp)
            ) {
                Text("Sign in")
            }
            Spacer(Modifier.height(8.dp))
            Text(text="Go to sign up",modifier = Modifier.clickable{
                navigateToSignUp()
            })
        }
    }
}