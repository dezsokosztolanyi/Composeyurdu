package com.kosztolanyi.composeyurdu.data.remote

import com.kosztolanyi.composeyurdu.models.HomeData
import com.kosztolanyi.composeyurdu.network.HomeDataApi
import com.kosztolanyi.composeyurdu.util.Resource
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

//class RemoteDataSource @Inject constructor(private val api: HomeDataApi) {
//
//    suspend fun getHomeData() : Resource<HomeData> {
//        val response = try {
//            api.getHomeData()
//        }catch (e : Exception){
//            return Resource.Error(e.message)
//        }
//        return Resource.Success(data = response.body()!!)
//    }
//}