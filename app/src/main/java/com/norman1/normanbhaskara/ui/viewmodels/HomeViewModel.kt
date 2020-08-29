package com.norman1.normanbhaskara.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.norman1.normanbhaskara.data.models.User
import com.norman1.normanbhaskara.data.repositories.UsersRepository
import com.norman1.normanbhaskara.utils.Resource

class HomeViewModel : ViewModel() {

    private val username: MutableLiveData<String> = MutableLiveData()

    val searchResult: LiveData<Resource<List<User>>> = Transformations
        .switchMap(username) {
            UsersRepository.searchUsers(it)
        }

    fun setUserId(query: String) {
        if (username.value == query) {
            return
        }
        username.value = query
    }
}
