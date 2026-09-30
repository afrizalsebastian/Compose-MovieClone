package com.github.afrizalsebastian.compose_movieclone.services

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class BaseApiService(
    protected val baseUrl: String,
    protected val interceptor: ((Interceptor.Chain) -> Response)? = null,
    protected val jsonStrategy: Gson = GsonBuilder()
        .create(),
    protected val client: OkHttpClient = NetworkService.httpClient
) {
    protected val retrofit: Retrofit by lazy {
        val newClient = if (interceptor != null) {
            client.newBuilder()
                .addInterceptor(interceptor)
                .build()

        } else client

        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(newClient)
            .addConverterFactory(GsonConverterFactory.create(jsonStrategy))
            .build()
    }

    protected inline fun <reified T> createService(): T {
        return retrofit.create(T::class.java)
    }
}