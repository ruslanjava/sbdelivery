package ru.skillbranch.sbdelivery.http

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.Retrofit2Platform
import retrofit2.converter.moshi.MoshiConverterFactory
import ru.skillbranch.sbdelivery.http.auth.AuthorizedCallAdapterFactory
import ru.skillbranch.sbdelivery.http.auth.AuthorizedInterceptor
import java.util.concurrent.TimeUnit

object SbDeliveryServiceFactory {

    val instance: SbDeliveryService by lazy {
        val factories: MutableList<CallAdapter.Factory>  = mutableListOf()
        factories.addAll(Retrofit2Platform.defaultCallAdapterFactories(null))

        // add default CallAdapter as the last item. This is mandatory!
        val authorizedMethods: MutableMap<Int, Boolean> = HashMap()
        val callAdapterFactory = AuthorizedCallAdapterFactory(factories, authorizedMethods)

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .client(provideOkHttpClient(authorizedMethods))
            .addCallAdapterFactory(callAdapterFactory)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl("https://sandbox.skill-branch.ru/")
            .build()

        retrofit.create(SbDeliveryService::class.java)
    }

    private fun provideOkHttpClient(authorizedMethods: MutableMap<Int, Boolean>): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.connectTimeout(10, TimeUnit.SECONDS)
        client.readTimeout(10, TimeUnit.SECONDS)
        client.writeTimeout(10, TimeUnit.SECONDS)

        val loggingInterceptor = provideLoggingInterceptor();
        client.addInterceptor(loggingInterceptor)

        client.addInterceptor(AuthorizedInterceptor(authorizedMethods))

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