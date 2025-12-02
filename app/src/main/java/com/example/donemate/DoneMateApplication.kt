package com.example.donemate

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class DoneMateApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}