package com.kosztolanyi.composeyurdu.repository

import com.kosztolanyi.composeyurdu.models.HomeData
import com.kosztolanyi.composeyurdu.network.HomeDataApi
import com.kosztolanyi.composeyurdu.util.Resource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@ActivityRetainedScoped
class ComposeyurduRepository @Inject constructor(private val api: HomeDataApi) {

    suspend fun getHomeData() : Resource<HomeData> {
        val response = try {
            api.getHomeData()
        }catch (e : Exception){
            return Resource.Error(e.message)
        }
        return Resource.Success(data = response)
    }
}