package com.example.donemate

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.example.donemate.ui.screens.sign_in.SignInScreen
import com.example.donemate.ui.screens.sign_in.SignInViewModel
import com.example.donemate.ui.screens.sign_up.SignUpScreen
import com.example.donemate.ui.screens.sign_up.SignUpViewModel
import kotlinx.serialization.Serializable


@Serializable
data object SignUp : NavKey

@Serializable
data object SignIn : NavKey

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun DoneMateApp() {
    val backStack = rememberNavBackStack(SignUp)
    val viewModelDecorator = rememberViewModelStoreNavEntryDecorator<NavKey>()
    val saveableStateDecorator = rememberSaveableStateHolderNavEntryDecorator<NavKey>()
    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            saveableStateDecorator,
            viewModelDecorator
        ),
        entryProvider = entryProvider {
            entry<SignUp>{
                val vm = hiltViewModel<SignUpViewModel>()
                SignUpScreen(navigateToSignIn = { backStack.add(SignIn) }, vm = vm)
            }
            entry<SignIn>{
                val vm = hiltViewModel<SignInViewModel>()
                SignInScreen(navigateToSignUp = {backStack.add(SignUp) }, vm = vm)
            }
        }
    )
}
