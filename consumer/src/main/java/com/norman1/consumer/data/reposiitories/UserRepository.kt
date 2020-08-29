package com.norman1.consumer.data.reposiitories

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.norman1.consumer.data.datasource.UserDataSource
import com.norman1.consumer.data.models.User
import kotlinx.coroutines.Dispatchers

class UserRepository(private val source: UserDataSource) {
    fun getUserList(): LiveData<List<User>> = liveData(Dispatchers.IO) {
        emit(source.fetchUsers())
    }
}