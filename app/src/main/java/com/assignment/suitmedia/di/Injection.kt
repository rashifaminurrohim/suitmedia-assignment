package com.assignment.suitmedia.di

import android.content.Context
import com.assignment.suitmedia.data.UserRepository
import com.assignment.suitmedia.database.UserDatabase
import com.assignment.suitmedia.network.ApiConfig

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val database = UserDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return UserRepository(database, apiService)
    }
}