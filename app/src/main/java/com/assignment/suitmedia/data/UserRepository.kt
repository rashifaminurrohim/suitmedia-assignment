package com.assignment.suitmedia.data

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.assignment.suitmedia.database.UserDatabase
import com.assignment.suitmedia.network.ApiService
import com.assignment.suitmedia.network.DataItem

class UserRepository(private val userDatabase: UserDatabase, private val apiService: ApiService) {
    fun getUsers(): LiveData<PagingData<DataItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 6
            ),
            remoteMediator = UserRemoteMediator(userDatabase, apiService),
            pagingSourceFactory = {
                userDatabase.userDao().getAllUser()
            }
        ).liveData
    }
}