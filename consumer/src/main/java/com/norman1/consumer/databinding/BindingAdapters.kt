package com.norman1.normanbhaskara.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.norman1.consumer.R

@BindingAdapter("avatar")
fun avatar(imageView: ImageView, avatar: String) {
    val circularProgressDrawable = CircularProgressDrawable(imageView.context)
    circularProgressDrawable.apply {
        setColorSchemeColors(R.color.colorPrimary)
        strokeWidth = 5f
        centerRadius = 30f
        start()
    }

    Glide.with(imageView)
        .asBitmap()
        .load(avatar)
        .into(imageView)
}
