package com.example.end_to_end_app.data.di

import com.example.end_to_end_app.data.api.interceptor.NetworkStatusInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
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
}