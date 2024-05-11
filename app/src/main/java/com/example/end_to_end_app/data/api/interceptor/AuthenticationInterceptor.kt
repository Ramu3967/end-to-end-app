package com.example.end_to_end_app.data.api.interceptor

import com.example.end_to_end_app.data.preferences.Preferences
import okhttp3.Interceptor
import okhttp3.Response

/**
 * this interceptor is used to
 * 1) add authentication headers
 * 2) refresh/manage the auth token
 * for each request from the client
 */
class AuthenticationInterceptor(
    private val preferences: Preferences
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = preferences.getToken()
        TODO("add logic")
    }
}