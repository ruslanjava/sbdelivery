package ru.skillbranch.sbdelivery.http

import okhttp3.ResponseBody
import retrofit2.http.*
import ru.skillbranch.sbdelivery.http.auth.Authorized
import ru.skillbranch.sbdelivery.http.data.auth.LoggedRes
import ru.skillbranch.sbdelivery.http.data.auth.LoginRes
import ru.skillbranch.sbdelivery.http.data.auth.RefreshTokenRes
import ru.skillbranch.sbdelivery.http.data.auth.RegisterRes
import ru.skillbranch.sbdelivery.http.data.dishes.CategoryRes
import ru.skillbranch.sbdelivery.http.data.dishes.DishRes
import ru.skillbranch.sbdelivery.http.data.dishes.FavoriteDishRes
import ru.skillbranch.sbdelivery.http.data.profile.ProfilePasswordRes
import ru.skillbranch.sbdelivery.http.data.profile.ProfileRes
import ru.skillbranch.sbdelivery.http.data.recovery.RecoveryCodeRes
import ru.skillbranch.sbdelivery.http.data.recovery.RecoveryPasswordRes
import ru.skillbranch.sbdelivery.http.data.recovery.RecoveryEmailRes
import ru.skillbranch.sbdelivery.http.data.review.NewReviewRes
import ru.skillbranch.sbdelivery.http.data.review.ReviewRes

interface SbDeliveryService {

    // -- авторизация --

    @POST("auth/login")
    suspend fun auth(@Body login: LoginRes): LoggedRes

    @POST("auth/register")
    suspend fun register(@Body register: RegisterRes): LoggedRes

    @POST("auth/refresh")
    suspend fun refreshToken(@Body body: RefreshTokenRes)

    @POST("auth/recovery/email")
    suspend fun recoveryEmail(@Body body: RecoveryEmailRes)

    @POST("auth/recovery/code")
    suspend fun recoveryCode(@Body body: RecoveryCodeRes)

    @POST("auth/recovery/password")
    suspend fun recoveryCode(@Body body: RecoveryPasswordRes)

    // -- категории и блюда --

    @GET("categories")
    suspend fun categories(
        @Query("offset") offset: Int, @Query("limit") limit: Int = 10,
        @Header("If-Modified-Since") ifModifiedSince: String = "Wed, 21 Oct 2015 07:28:00 GMT"
    ): List<CategoryRes>

    @GET("/main/recommend")
    suspend fun recommendedIds() : List<Int>

    @GET("/dishes")
    suspend fun dishes(
        @Query("offset") offset: Int, @Query("limit") limit: Int = 10,
        @Header("If-Modified-Since") ifModifiedSince: String = "Wed, 21 Oct 2015 07:28:00 GMT"
    ): List<DishRes>

    // -- избранное --

    @Authorized
    @GET("/favorite")
    suspend fun favoriteDishes(
        @Query("offset") offset: Int, @Query("limit") limit: Int = 10,
        @Header("If-Modified-Since") ifModifiedSince: String = "Wed, 21 Oct 2015 07:28:00 GMT"
    ): List<FavoriteDishRes>

    @Authorized
    @PUT("/favorite")
    suspend fun favoriteDish(@Body favoriteDish: FavoriteDishRes): ResponseBody

    // -- профиль --

    @Authorized
    @GET("profile")
    suspend fun profile(): ProfileRes

    @Authorized
    @PUT("profile")
    suspend fun profile(@Body profile: ProfileRes)

    @Authorized
    @PUT("profile/password")
    suspend fun profilePassword(@Body password: ProfilePasswordRes)

    // -- отзывы --

    @GET("/reviews/{dishId}")
    suspend fun reviews(
        @Path("dishId") dishId: String, @Query("offset") offset: Int, @Query("limit") limit: Int = 10,
        @Header("If-Modified-Since") ifModifiedSince: String = "Wed, 21 Oct 2015 07:28:00 GMT"
    ): List<ReviewRes>

    @Authorized
    @POST("/reviews/{dishId}")
    suspend fun addReview(@Path("dishId") dishId: String, @Body newReview: NewReviewRes)

}