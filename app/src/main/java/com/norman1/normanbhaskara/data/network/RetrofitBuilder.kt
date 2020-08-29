package com.norman1.normanbhaskara.data.network

import com.norman1.normanbhaskara.BuildConfig.BASE_URL
import com.norman1.normanbhaskara.BuildConfig.GITHUB_TOKEN
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitBuilder {

    private val client by lazy {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .header("Authorization", GITHUB_TOKEN)
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    private val retrofit: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
    }

    val apiGithub: ApiGithub by lazy {
        retrofit.build().create(ApiGithub::class.java)
    }
}