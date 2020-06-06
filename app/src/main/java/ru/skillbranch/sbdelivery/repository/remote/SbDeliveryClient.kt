package ru.skillbranch.sbdelivery.repository.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object SbDeliveryClient {

    val instance: SbDeliveryService by lazy {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .client(provideOkHttpClient())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl("https://polls.apiblueprint.org/")
            .build()

        retrofit.create(SbDeliveryService::class.java)
    }

    private fun provideOkHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.connectTimeout(10, TimeUnit.SECONDS)
        client.readTimeout(10, TimeUnit.SECONDS)
        client.writeTimeout(10, TimeUnit.SECONDS)

        val loggingInterceptor =
            provideLoggingInterceptor();
        client.addInterceptor(loggingInterceptor)
        return client.build()
    }

    private fun provideLoggingInterceptor() : HttpLoggingInterceptor {
        val logger = object: HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                println(message)
            }
        }
        val interceptor = HttpLoggingInterceptor(logger)
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

}