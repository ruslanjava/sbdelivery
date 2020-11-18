package ru.skillbranch.sbdelivery.http.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import ru.skillbranch.sbdelivery.http.NetworkMonitor
import ru.skillbranch.sbdelivery.http.errors.NoNetworkError

class NetworkStatusInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        // return response or throw error
        if (!NetworkMonitor.isConnected) throw NoNetworkError()

        return chain.proceed(chain.request())
    }

}