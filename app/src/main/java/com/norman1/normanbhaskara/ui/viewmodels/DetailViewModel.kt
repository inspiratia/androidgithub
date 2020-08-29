package com.norman1.normanbhaskara.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.norman1.normanbhaskara.data.local.UserDao
import com.norman1.normanbhaskara.data.local.UserDatabase
import com.norman1.normanbhaskara.data.models.User
import com.norman1.normanbhaskara.data.repositories.UserDetailRepository
import com.norman1.normanbhaskara.utils.Resource
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private var userDao: UserDao = UserDatabase.getDatabase(application).userDao()

    private var userDetailRepository: UserDetailRepository

    init {
        userDetailRepository = UserDetailRepository(userDao)
    }

    fun data(username: String): LiveData<Resource<User>> =
        userDetailRepository.getDetailUser(username)

    fun addFavorite(user: User) = viewModelScope.launch {
        userDetailRepository.insert(user)
    }

    fun removeFavorite(user: User) = viewModelScope.launch {
        userDetailRepository.delete(user)
    }

    val isFavorite: LiveData<Boolean> = userDetailRepository.isFavorite
}