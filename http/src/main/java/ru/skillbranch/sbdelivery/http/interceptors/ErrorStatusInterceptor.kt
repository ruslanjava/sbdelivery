package ru.skillbranch.sbdelivery.http.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import ru.skillbranch.sbdelivery.http.errors.ApiError

class ErrorStatusInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val res = chain.proceed(chain.request())

        if (res.isSuccessful) {
            return res
        }

        val errMessage = res.body!!.string()

        when (res.code) {
            304 -> throw ApiError.NotModified(errMessage)
            400 -> throw ApiError.BadRequest(errMessage)
            401 -> throw ApiError.Unauthorized(errMessage)
            403 -> throw ApiError.Forbidden(errMessage)
            404 -> throw ApiError.NotFound(errMessage)
            500 -> throw ApiError.InternalServerError(errMessage)
            else -> throw ApiError.UnknownError(errMessage)
        }
    }

}