package com.kosztolanyi.composeyurdu.network

import com.kosztolanyi.composeyurdu.models.HomeData
import retrofit2.Response
import retrofit2.http.GET

interface HomeDataApi {

    @GET("popular.json")
    suspend fun getHomeData() : HomeData

}