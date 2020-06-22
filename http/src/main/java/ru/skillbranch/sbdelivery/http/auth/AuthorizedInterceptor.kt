package ru.skillbranch.sbdelivery.http.auth

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

internal class AuthorizedInterceptor(private val authorizedMethods: Map<Int, Boolean>) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val key = request.identity()
        val authorizedMethod = authorizedMethods[key]
        if (authorizedMethod == true) {
            val token = AuthTokenContainer.token
            if (token != null) {
                // modify the request dependending on the info
                request = request
                    .newBuilder()
                    .addHeader("Authorization", "Bearer $token").build()
            }
        }
        return chain.proceed(request)
    }

}