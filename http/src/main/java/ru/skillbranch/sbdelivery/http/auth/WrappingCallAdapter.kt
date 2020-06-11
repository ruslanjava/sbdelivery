package ru.skillbranch.sbdelivery.http.auth

import okhttp3.Request
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

internal class WrappingCallAdapter<R, T>(
    private val adapter: CallAdapter<R, T>,
    private val auth: MutableMap<Int, Boolean>,
    private val enabled: Boolean
) : CallAdapter<R, T> {

    override fun responseType(): Type {
        return adapter.responseType()
    }

    override fun adapt(call: Call<R>): T {
        val request = call.request()
        auth[identify(request)] = enabled
        return adapter.adapt(call)
    }

    private fun identify(request: Request): Int {
        // this is very experimental but it does the job currently
        return (request.url.toString() + request.method).hashCode()
    }

}