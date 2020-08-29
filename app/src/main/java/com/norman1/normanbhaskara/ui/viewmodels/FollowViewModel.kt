package com.norman1.normanbhaskara.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.norman1.normanbhaskara.data.models.User
import com.norman1.normanbhaskara.data.repositories.UsersRepository
import com.norman1.normanbhaskara.utils.Resource
import com.norman1.normanbhaskara.utils.TypeView

class FollowViewModel : ViewModel() {

    private val username: MutableLiveData<String> = MutableLiveData()

    private lateinit var type: TypeView

    val dataFollow: LiveData<Resource<List<User>>> = Transformations
        .switchMap(username) {
            when (type) {
                TypeView.FOLLOWER -> {
                    UsersRepository.getFollowers(it)
                }
                TypeView.FOLLOWING -> {
                    UsersRepository.getFollowing(it)
                }
            }
        }

    fun setParam(user: String, typeView: TypeView) {
        if (username.value == user) {
            return
        }
        username.value = user
        type = typeView
    }
}