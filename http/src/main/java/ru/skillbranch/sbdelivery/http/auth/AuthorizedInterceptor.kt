package ru.skillbranch.sbdelivery.http.auth

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import ru.skillbranch.sbdelivery.http.auth.AuthTokenContainer.token
import java.io.IOException

internal class AuthorizedInterceptor(private val authorizedMethods: Map<Int, Boolean>) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val authorizedMethod = authorizedMethods[identify(request)]
        if (authorizedMethod == true) {
            val token = token ?: throw NotAuthorizedException()

            // modify the request dependending on the info
            request = request
                .newBuilder()
                .addHeader("Authorization", "Bearer $token").build()
        }
        return chain.proceed(request)
    }

    private fun identify(request: Request): Int {
        // make sure this is the same method you use in the CallAdapter
        return (request.url.toString() + request.method).hashCode()
    }

}