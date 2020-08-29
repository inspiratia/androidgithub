package com.norman1.normanbhaskara.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.norman1.normanbhaskara.data.local.UserDao
import com.norman1.normanbhaskara.data.models.User
import com.norman1.normanbhaskara.data.network.RetrofitBuilder
import com.norman1.normanbhaskara.utils.Resource
import kotlinx.coroutines.Dispatchers

class UserDetailRepository(private val userDao: UserDao) {

    private val favorite: MutableLiveData<Boolean> = MutableLiveData()

    fun getDetailUser(username: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        val user = userDao.getUserDetails(username)
        if (user != null) {
            favorite.postValue(true)
            emit(Resource.success(data = user))
        } else {
            favorite.postValue(false)
            try {
                emit(Resource.success(data = RetrofitBuilder.apiGithub.userDetail(username)))
            } catch (exception: Exception) {
                emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
            }
        }
    }

    suspend fun insert(user: User) {
        userDao.insertUser(user)
        favorite.value = true
    }

    suspend fun delete(user: User) {
        userDao.deleteUser(user)
        favorite.value = false
    }

    val isFavorite: LiveData<Boolean> = favorite
}