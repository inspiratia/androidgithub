package com.norman1.normanbhaskara.data.network.responese

import android.os.Parcelable
import com.norman1.normanbhaskara.data.models.User
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchResponse(
    val total_count: String,
    val incomplete_results: Boolean? = null,
    val items: List<User>
) : Parcelable