package com.sharkt.api

class SingletonDemo private constructor(private val property: Int) {
    companion object {
        @Volatile
        private var instance: SingletonDemo? = null

        fun getInstance(property: Int) =
            instance ?: synchronized(this) {
                instance ?: SingletonDemo(property).also { instance = it }
            }
    }
}