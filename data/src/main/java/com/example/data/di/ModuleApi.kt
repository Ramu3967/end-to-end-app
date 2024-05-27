package com.example.data.di

import com.example.data.api.ApiConstants.BASE_ENDPOINT
import com.example.data.api.IPetFinderApi
import com.example.data.api.interceptor.AuthenticationInterceptor
import com.example.data.api.interceptor.NetworkStatusInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModuleApi {

    @Provides
    @Singleton
    fun provideOkHttpClient(
        networkStatusInterceptor: NetworkStatusInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authenticationInterceptor: AuthenticationInterceptor
    ): OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(networkStatusInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(authenticationInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit.Builder =
        Retrofit.Builder()
            .baseUrl(BASE_ENDPOINT)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())


    @Provides
    @Singleton
    fun providePetFinderApi(
        builder: Retrofit.Builder
    ): IPetFinderApi = builder
        .build()
        .create(IPetFinderApi::class.java)


    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()

        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return interceptor
    }

}

