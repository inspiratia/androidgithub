package com.norman1.consumer.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id: Int,
    val login: String,
    val avatar_url: String,
    val name: String?,
    val location: String?,
    val type: String?
) : Parcelable