package com.assignment.suitmedia.view

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.assignment.suitmedia.di.Injection
import com.assignment.suitmedia.data.UserRepository
import com.assignment.suitmedia.network.DataItem

class ThirdScreenViewModel(repository: UserRepository) : ViewModel() {
    val user: LiveData<PagingData<DataItem>> =
        repository.getUsers().cachedIn(viewModelScope)

    class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ThirdScreenViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ThirdScreenViewModel(Injection.provideRepository(context)) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}