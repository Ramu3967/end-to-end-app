package com.example.end_to_end_app.data.di

import com.example.end_to_end_app.data.api.ApiConstants
import com.example.end_to_end_app.data.api.PetFinderApi
import com.example.end_to_end_app.data.api.interceptor.NetworkStatusInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(
        networkStatusInterceptor: NetworkStatusInterceptor
    ): OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(networkStatusInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit.Builder =
        Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_ENDPOINT)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())


    @Provides
    @Singleton
    fun providePetFinderApi(
        builder: Retrofit.Builder
    ): PetFinderApi = builder
        .build()
        .create(PetFinderApi::class.java)

}