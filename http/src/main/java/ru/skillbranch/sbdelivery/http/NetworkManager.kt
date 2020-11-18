package ru.skillbranch.sbdelivery.http

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.skillbranch.sbdelivery.http.JsonConverter.moshi
import ru.skillbranch.sbdelivery.http.interceptors.ErrorStatusInterceptor
import ru.skillbranch.sbdelivery.http.interceptors.NetworkStatusInterceptor
import ru.skillbranch.sbdelivery.http.interceptors.TokenAuthenticator
import timber.log.Timber
import java.util.concurrent.TimeUnit

object NetworkManager {

    val api: RestService by lazy {

        val retrofit: Retrofit = Retrofit.Builder()
            .client(provideOkHttpClient())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl("https://sandbox.skill-branch.ru/")
            .build()

        retrofit.create(RestService::class.java)
    }

    private fun provideOkHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.connectTimeout(10, TimeUnit.SECONDS)
        client.readTimeout(10, TimeUnit.SECONDS)
        client.writeTimeout(10, TimeUnit.SECONDS)

        val loggingInterceptor = provideLoggingInterceptor()

        client.addInterceptor(loggingInterceptor)
            .authenticator(TokenAuthenticator())
            .addInterceptor(NetworkStatusInterceptor()) // intercept network status
            .addInterceptor(loggingInterceptor) // intercept req/res for logging
            .addInterceptor(ErrorStatusInterceptor()) // intercept status errors

        return client.build()
    }

    private fun provideLoggingInterceptor() : HttpLoggingInterceptor {
        val logger = object: HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Timber.d(message)
            }
        }
        val interceptor = HttpLoggingInterceptor(logger)
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

}