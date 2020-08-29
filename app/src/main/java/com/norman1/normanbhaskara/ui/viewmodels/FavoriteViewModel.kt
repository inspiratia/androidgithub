package com.norman1.normanbhaskara.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.norman1.normanbhaskara.data.local.UserDatabase
import com.norman1.normanbhaskara.data.models.User
import com.norman1.normanbhaskara.data.repositories.UsersRepository

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {

    val dataFavorite: LiveData<List<User>>

    init {
        val userDao = UserDatabase.getDatabase(application).userDao()
        dataFavorite = UsersRepository.getFavorite(userDao)
    }
}