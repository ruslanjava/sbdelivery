package ru.skillbranch.sbdelivery.repository.remote

import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import ru.skillbranch.sbdelivery.data.remote.FavoriteDish

interface SbDeliveryService {

    @GET("main/recommend")
    suspend fun recommendedIds() : List<Int>

    @GET("favorite?offset={offset}&limit={limit}")
    suspend fun favoriteDishes(@Path("offset") offset: Int, @Path("limit") limit: Int): List<FavoriteDish>

    @PUT("favorite")
    suspend fun favorite(@Body favoriteDish: FavoriteDish): ResponseBody



}