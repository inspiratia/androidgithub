package com.norman1.normanbhaskara.data.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "user_table")
@Parcelize
data class User(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    @ColumnInfo(name = "login")
    val login: String,
    @ColumnInfo(name = "avatar_url")
    val avatar_url: String,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "company")
    val company: String?,
    @ColumnInfo(name = "location")
    val location: String?,
    @ColumnInfo(name = "public_repos")
    val public_repos: String?,
    @ColumnInfo(name = "following")
    val following: String?,
    @ColumnInfo(name = "followers")
    val followers: String?,
    @ColumnInfo(name = "type")
    val type: String?
) : Parcelable