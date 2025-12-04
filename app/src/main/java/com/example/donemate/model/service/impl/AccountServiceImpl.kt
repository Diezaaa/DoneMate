package com.example.donemate.model.service.impl

import android.util.Log
import androidx.compose.ui.util.trace
import com.example.donemate.model.User
import com.example.donemate.model.service.AccountService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.Firebase
import com.google.firebase.auth.EmailAuthProvider
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AccountServiceImpl @Inject constructor(private val auth: FirebaseAuth) : AccountService {


    override val currentUserId: String
        get() = auth.currentUser?.uid.orEmpty()

    override val hasUser: Boolean
        get() = auth.currentUser != null

    override val currentUser: Flow<User>
        get() = callbackFlow {
            val listener =
                FirebaseAuth.AuthStateListener { auth ->
                    this.trySend(auth.currentUser?.let { User(it.uid, it.isAnonymous, it.email) } ?: User())
                }
            auth.addAuthStateListener(listener)
            awaitClose { auth.removeAuthStateListener(listener) }
        }

    override suspend fun authenticate(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).await()
    }
    override suspend fun createAnonymousAccount() {
        auth.signInAnonymously().await()
    }


    override suspend fun linkAccount(email: String, password: String) {
        val TAG = "linkAccount"

        Log.d(TAG, "Starting to link account for email: $email")

        try {
            val credential = EmailAuthProvider.getCredential(email, password)
            val result = auth.currentUser!!.linkWithCredential(credential).await()
            Log.d(TAG, "Account linked successfully for user: ${auth.currentUser!!.uid}")
        } catch (e: Exception) {
            Log.e(TAG, "Failed to link account for email: $email", e)
            throw e
        }
    }

    override suspend fun deleteAccount() {
        auth.currentUser!!.delete().await()
    }

    override suspend fun signOut() {
        if (auth.currentUser!!.isAnonymous) {
            auth.currentUser!!.delete()
        }
        auth.signOut()

        // Sign the user back in anonymously.
        createAnonymousAccount()
    }

    companion object {
        private const val LINK_ACCOUNT_TRACE = "linkAccount"
    }
}

