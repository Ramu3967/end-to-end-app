package com.example.end_to_end_app.common.data.api.interceptor

import com.example.end_to_end_app.common.data.api.ConnectionManager
import com.example.end_to_end_app.common.domain.NetworkUnavailableException
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class NetworkStatusInterceptor @Inject constructor(
    private val connectionManager: ConnectionManager
): Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        return if(connectionManager.isConnected){
            chain.proceed(chain.request())
        }else {
            // this exception is placed in domain
            throw NetworkUnavailableException()
        }
    }
}
