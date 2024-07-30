package com.ffandroidproj4.androiddevp4

import android.app.Application
import android.util.Log
import com.ffandroidproj4.androiddevp4.preferences.PreferenceManager

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Log.e("MyApplication", "onCreate: Chal to gya ha" )
        PreferenceManager.init(this)
    }
}