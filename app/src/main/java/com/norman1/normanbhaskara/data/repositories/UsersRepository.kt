package com.norman1.normanbhaskara.data.repositories

import androidx.lifecycle.liveData
import com.norman1.normanbhaskara.data.local.UserDao
import com.norman1.normanbhaskara.data.network.RetrofitBuilder
import com.norman1.normanbhaskara.utils.Resource
import kotlinx.coroutines.Dispatchers

object UsersRepository {

    fun searchUsers(query: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            val userSearch = RetrofitBuilder.apiGithub.searchUsers(query)
            emit(Resource.success(data = userSearch.items))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getFollowers(username: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = RetrofitBuilder.apiGithub.userFollower(username)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getFollowing(username: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = RetrofitBuilder.apiGithub.userFollowing(username)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getFavorite(userDao: UserDao) = userDao.getUserList()
}