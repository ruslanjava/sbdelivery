package ru.skillbranch.sbdelivery.http

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import ru.skillbranch.sbdelivery.http.data.auth.*
import ru.skillbranch.sbdelivery.http.data.dishes.CategoryRes
import ru.skillbranch.sbdelivery.http.data.dishes.DishRes
import ru.skillbranch.sbdelivery.http.data.dishes.FavoriteDishRes
import ru.skillbranch.sbdelivery.http.data.profile.ProfilePasswordReq
import ru.skillbranch.sbdelivery.http.data.profile.ProfileRes
import ru.skillbranch.sbdelivery.http.data.recovery.RecoveryCodeReq
import ru.skillbranch.sbdelivery.http.data.recovery.RecoveryPasswordReq
import ru.skillbranch.sbdelivery.http.data.recovery.RecoveryEmailReq
import ru.skillbranch.sbdelivery.http.data.review.NewReviewRes
import ru.skillbranch.sbdelivery.http.data.review.ReviewRes

interface RestService {

    // -- авторизация --

    @POST("/auth/login")
    suspend fun auth(@Body login: LoginReq): LoggedRes

    @POST("/auth/register")
    suspend fun register(@Body register: RegisterReq): LoggedRes

    @POST("/auth/refresh")
    fun refreshToken(@Body body: RefreshTokenReq): Call<AccessTokenRes>

    @POST("/auth/recovery/email")
    suspend fun recoveryEmail(@Body body: RecoveryEmailReq)

    @POST("/auth/recovery/code")
    suspend fun recoveryCode(@Body body: RecoveryCodeReq)

    @POST("/auth/recovery/password")
    suspend fun recoveryCode(@Body body: RecoveryPasswordReq)

    // -- категории и блюда --

    @GET("/categories")
    suspend fun categories(
        @Query("offset") offset: Int, @Query("limit") limit: Int = 10,
        @Header("If-Modified-Since") ifModifiedSince: String = "Wed, 21 Oct 2015 07:28:00 GMT"
    ): List<CategoryRes>

    @GET("/main/recommend")
    suspend fun recommendedIds() : List<String>

    @GET("/dishes")
    suspend fun dishes(
        @Query("offset") offset: Int, @Query("limit") limit: Int = 10,
        @Header("If-Modified-Since") ifModifiedSince: String = "Wed, 21 Oct 2015 07:28:00 GMT"
    ): List<DishRes>

    // -- избранное --

    // Authorized
    @GET("/favorite")
    suspend fun favoriteDishes(
        @Query("offset") offset: Int, @Query("limit") limit: Int = 10,
        @Header("If-Modified-Since") ifModifiedSince: String = "Wed, 21 Oct 2015 07:28:00 GMT"
    ): List<FavoriteDishRes>

    // Authorized
    @PUT("/favorite")
    suspend fun favoriteDish(@Body favoriteDish: FavoriteDishRes): ResponseBody

    // -- профиль --

    // Authorized
    @GET("/profile")
    suspend fun profile(): ProfileRes

    // Authorized
    @PUT("/profile")
    suspend fun profile(@Body profile: ProfileRes)

    // Authorized
    @PUT("/profile/password")
    suspend fun profilePassword(@Body password: ProfilePasswordReq)

    // -- отзывы --

    @GET("/reviews/{dishId}")
    suspend fun reviews(
        @Path("dishId") dishId: String, @Query("offset") offset: Int, @Query("limit") limit: Int = 10,
        @Header("If-Modified-Since") ifModifiedSince: String = "1970-01-01T00:00:00.000Z"
    ): List<ReviewRes>

    // Authorized
    @POST("/reviews/{dishId}")
    suspend fun addReview(@Path("dishId") dishId: String, @Body newReview: NewReviewRes)

}