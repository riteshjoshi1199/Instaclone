package com.ritesh.instaclone.app

class AuthRepository(private val sessionTime: Long) {


    companion object {
        private var instance: AuthRepository? = null

        fun getInstance(sessionTime: Long): AuthRepository {
            val newInstance = if (instance == null) {
                AuthRepository(sessionTime)
            } else if (instance!!.sessionTime != sessionTime) {
                AuthRepository(sessionTime)
            } else {
                instance!!
            }

            return newInstance
        }
    }
}