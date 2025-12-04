package com.example.donemate.ui.screens.sign_up

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.donemate.model.service.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountService: AccountService
) : ViewModel()  {
    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    fun onEmailChange(newValue: String) {
        _uiState.value = _uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        _uiState.value = _uiState.value.copy(password = newValue)
    }
    fun onSignUpClick() {
        viewModelScope.launch {
            accountService.linkAccount(_uiState.value.email, _uiState.value.password)
        }
    }

    fun onAppStart(){
        if (!accountService.hasUser) {
            viewModelScope.launch {
                accountService.createAnonymousAccount()
            }
        }
    }

}

data class SignUpUiState(
    val username: String = "",
    val email: String = "",
    val password: String = ""
)