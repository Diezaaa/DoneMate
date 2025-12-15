package com.example.donemate.model.service.module

import com.example.donemate.BuildConfig
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.firestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Provides
    @Singleton
    fun auth(): FirebaseAuth {
        val auth = FirebaseAuth.getInstance()
        if (android.os.Debug.isDebuggerConnected()) {
            // Connect to local emulator
            auth.useEmulator("10.0.2.2", 9099) // 10.0.2.2 is localhost for Android Emulator
        }
        return auth
    }
    @Provides
    @Singleton
    fun firestore(): FirebaseFirestore{
        val firestore = FirebaseFirestore.getInstance()
        if (android.os.Debug.isDebuggerConnected()) {
            firestore.useEmulator("10.0.2.2", 8080)
            firestore.firestoreSettings = FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build()
        }
        return firestore
    }

}