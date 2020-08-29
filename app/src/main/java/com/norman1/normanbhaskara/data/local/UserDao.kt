package com.norman1.normanbhaskara.data.local

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import com.norman1.normanbhaskara.data.models.User

@Dao
interface UserDao {
    @Query("SELECT * from user_table ORDER BY login ASC")
    fun getUserList(): LiveData<List<User>>

    @Query("SELECT * from user_table WHERE login = :username")
    fun getUserDetails(username: String): User?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUser(user: User)

    @Delete
    suspend fun deleteUser(model: User): Int

    @Query("DELETE FROM user_table")
    suspend fun deleteAll()

    @Query("SELECT * from user_table ORDER BY login ASC")
    fun getUserListProvider(): Cursor

    @Query("SELECT * from user_table ORDER BY login ASC")
    fun getWidgetList(): List<User>
}