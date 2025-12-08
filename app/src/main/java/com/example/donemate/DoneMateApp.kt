package com.example.donemate

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import com.example.donemate.ui.screens.add.AddScreen
import com.example.donemate.ui.screens.add.AddViewModel
import com.example.donemate.ui.screens.sign_in.SignInScreen
import com.example.donemate.ui.screens.sign_in.SignInViewModel
import com.example.donemate.ui.screens.sign_up.SignUpScreen
import com.example.donemate.ui.screens.sign_up.SignUpViewModel
import com.example.donemate.ui.screens.task.EditScreen
import com.example.donemate.ui.screens.task.EditViewModel
import com.example.donemate.ui.screens.tasks.TasksScreen
import com.example.donemate.ui.screens.tasks.TasksViewModel
import com.example.donemate.ui.theme.DoneMateTheme
import kotlinx.serialization.Serializable
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.donemate.ui.screens.account.AccountScreen
import com.example.donemate.ui.screens.account.AccountViewModel

@Serializable
data object SignUp : NavKey

@Serializable
data object SignIn : NavKey

@Serializable
data object Tasks : TopLevelRoute { override val icon = Icons.Default.Menu }

@Serializable
data class Edit(val id: String) : NavKey

@Serializable
data object Add : NavKey

private sealed interface TopLevelRoute : NavKey {
    val icon: ImageVector
}
@Serializable
data object Account : TopLevelRoute { override val icon = Icons.Default.AccountCircle }

private val TOP_LEVEL_ROUTES : List<TopLevelRoute> = listOf(Tasks, Account)


@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun DoneMateApp() {
    DoneMateTheme {
        val backStack = rememberNavBackStack(Tasks)
        val viewModelDecorator = rememberViewModelStoreNavEntryDecorator<NavKey>()
        val saveableStateDecorator = rememberSaveableStateHolderNavEntryDecorator<NavKey>()
        val currentDestination = backStack.lastOrNull()
        val showBottomBar = currentDestination in TOP_LEVEL_ROUTES

        Scaffold(
            bottomBar = {
                if (showBottomBar) {
                    NavigationBar {
                        TOP_LEVEL_ROUTES.forEach { topLevelRoute ->
                            val isSelected = topLevelRoute == currentDestination
                            NavigationBarItem(
                                selected = isSelected,
                                onClick = { backStack.add(topLevelRoute) },
                                label = { Text(topLevelRoute.toString()) },
                                icon = {
                                    Icon(
                                        imageVector = topLevelRoute.icon,
                                        contentDescription = null
                                    )
                                }
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            NavDisplay(
                backStack = backStack,
                onBack = { backStack.removeLastOrNull() },
                entryDecorators = listOf(
                    saveableStateDecorator,
                    viewModelDecorator
                ),
                modifier = Modifier
                    .systemBarsPadding()
                    .padding(innerPadding)
                    .padding(10.dp, 0.dp),
                entryProvider = entryProvider {
                    entry<SignUp> {
                        val viewModel = hiltViewModel<SignUpViewModel>()
                        SignUpScreen(
                            navigateToTasks = {
                                backStack.clear()
                                backStack.add(Tasks) },
                            navigateToSignIn = { backStack.add(SignIn) },
                            vm = viewModel
                        )
                    }
                    entry<SignIn> {
                        val viewModel = hiltViewModel<SignInViewModel>()
                        SignInScreen(navigateToSignUp = { backStack.add(SignUp) }, navigateToSuccess = {
                            backStack.clear()
                            backStack.add(Tasks)
                        }, vm = viewModel)
                    }
                    entry<Tasks> {
                        val viewModel = hiltViewModel<TasksViewModel>()
                        TasksScreen(
                            navigateToAdd = { backStack.add(Add) },
                            navigateToEdit = { taskId -> backStack.add(Edit(taskId)) },
                            vm = viewModel
                        )
                    }
                    entry<Edit> { task ->
                        val vm = hiltViewModel<EditViewModel>()
                        EditScreen(
                            id = task.id,
                            vm = vm
                        )
                    }
                    entry<Add> {
                        val vm = hiltViewModel<AddViewModel>()
                        AddScreen(
                            vm = vm
                        )
                    }
                    entry<Account> {
                        val vm = hiltViewModel<AccountViewModel>()
                        AccountScreen(
                            navigateToSignIn = {backStack.clear()
                                               backStack.add(SignIn)},
                            vm = vm,
                        )
                    }
                }
            )
        }
    }
}
