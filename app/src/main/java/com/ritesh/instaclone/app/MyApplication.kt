package com.ritesh.instaclone.app

import android.app.Application

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        authRepository = AuthRepository.getInstance(System.currentTimeMillis())
    }


    companion object {
        lateinit var authRepository: AuthRepository

    }
}