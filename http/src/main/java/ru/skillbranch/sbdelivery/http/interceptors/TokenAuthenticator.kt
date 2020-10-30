package ru.skillbranch.sbdelivery.http.interceptors

import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import ru.skillbranch.sbdelivery.http.HttpClientApplication
import ru.skillbranch.sbdelivery.http.NetworkManager
import ru.skillbranch.sbdelivery.http.data.auth.RefreshTokenReq
import ru.skillbranch.sbdelivery.prefs.PrefLogin
import java.net.HttpURLConnection

class TokenAuthenticator: Authenticator {

    private val login by lazy {
        PrefLogin(HttpClientApplication.applicationContext())
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.code != HttpURLConnection.HTTP_UNAUTHORIZED) {
            return null
        }

        val refreshTokenRes = RefreshTokenReq(login.token!!)
        val res = NetworkManager.api.refreshToken(refreshTokenRes).execute()

        if (!res.isSuccessful) {
            return null
        }

        val refreshRes = res.body()!!

        login.token = "Bearer ${refreshRes.accessToken}"

        return response.request.newBuilder()
            .header("Authorization", login.token!!)
            .build()
    }

}