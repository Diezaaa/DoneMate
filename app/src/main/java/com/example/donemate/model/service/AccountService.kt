package com.example.donemate.model.service

import com.example.donemate.model.User
import kotlinx.coroutines.flow.Flow
interface AccountService {
    val currentUserId: String
    val hasUser: Flow<Boolean>
    val currentUser: Flow<User?>
    suspend fun authenticate(email: String, password: String)
    suspend fun createAnonymousAccount()
    suspend fun linkAccount(email: String, password: String)
    suspend fun deleteAccount()
    suspend fun signOut()
    suspend fun createAccount(email: String, password: String)
}
