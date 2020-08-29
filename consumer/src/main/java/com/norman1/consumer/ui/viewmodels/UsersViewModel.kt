package com.norman1.consumer.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.norman1.consumer.data.datasource.UserDataSource
import com.norman1.consumer.data.reposiitories.UserRepository

class UsersViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UserRepository

    init {
        val source = UserDataSource(application.contentResolver)
        repository = UserRepository(source)
    }

    var userLists = repository.getUserList()
}