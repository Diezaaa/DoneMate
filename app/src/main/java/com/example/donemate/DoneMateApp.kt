package com.example.donemate

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
import com.example.donemate.ui.screens.tasks.TasksScreen
import com.example.donemate.ui.screens.tasks.TasksViewModel
import com.example.donemate.ui.theme.DoneMateTheme
import kotlinx.serialization.Serializable


@Serializable
data object SignUp : NavKey

@Serializable
data object SignIn : NavKey

@Serializable
data object Tasks : NavKey

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun DoneMateApp() {
    DoneMateTheme {
        val backStack = rememberNavBackStack(Tasks)
        val viewModelDecorator = rememberViewModelStoreNavEntryDecorator<NavKey>()
        val saveableStateDecorator = rememberSaveableStateHolderNavEntryDecorator<NavKey>()
        NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            entryDecorators = listOf(
                saveableStateDecorator,
                viewModelDecorator
            ),
            modifier = Modifier.systemBarsPadding().padding(10.dp, 0.dp),
            entryProvider = entryProvider {
                entry<SignUp> {
                    val viewModel = hiltViewModel<SignUpViewModel>()
                    SignUpScreen(
                        navigateToTasks = { backStack.add(Tasks) },
                        navigateToSignIn = { backStack.add(SignIn) },
                        vm = viewModel
                    )
                }
                entry<SignIn> {
                    val viewModel = hiltViewModel<SignInViewModel>()
                    SignInScreen(navigateToSignUp = { backStack.add(SignUp) }, vm = viewModel)
                }
                entry<Tasks> {
                    val viewModel = hiltViewModel<TasksViewModel>()
                    TasksScreen(vm = viewModel)
                }
            }
        )
    }
}
