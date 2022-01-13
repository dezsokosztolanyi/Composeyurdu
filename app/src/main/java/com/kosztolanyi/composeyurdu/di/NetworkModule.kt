package com.kosztolanyi.composeyurdu.di

import com.google.firebase.firestore.FirebaseFirestore
import com.kosztolanyi.composeyurdu.data.repositories.FirestoreRepository
import com.kosztolanyi.composeyurdu.network.HomeDataApi
import com.kosztolanyi.composeyurdu.repository.ComposeyurduRepository
import com.kosztolanyi.composeyurdu.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit() : HomeDataApi{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HomeDataApi::class.java)
    }

//    @Singleton
//    @Provides
//    fun provideRemoteDataSource(api: HomeDataApi) : RemoteDataSource{
//        return RemoteDataSource(api = api)
//    }

    @Singleton
    @Provides
    fun provideRepository(api: HomeDataApi) : ComposeyurduRepository{
        return ComposeyurduRepository(api = api)
    }

    @Singleton
    @Provides
    fun provideFireBookRepository() = FirestoreRepository(firestore = FirebaseFirestore.getInstance())


}