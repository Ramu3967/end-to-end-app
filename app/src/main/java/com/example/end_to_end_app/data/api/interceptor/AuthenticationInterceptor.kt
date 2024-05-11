package com.example.end_to_end_app.data.api.interceptor

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.end_to_end_app.data.api.ApiConstants
import com.example.end_to_end_app.data.api.ApiConstants.AUTH_ENDPOINT
import com.example.end_to_end_app.data.api.ApiParameters.AUTH_HEADER
import com.example.end_to_end_app.data.api.ApiParameters.CLIENT_ID
import com.example.end_to_end_app.data.api.ApiParameters.CLIENT_SECRET
import com.example.end_to_end_app.data.api.ApiParameters.GRANT_TYPE_KEY
import com.example.end_to_end_app.data.api.ApiParameters.GRANT_TYPE_VALUE
import com.example.end_to_end_app.data.api.ApiParameters.TOKEN_TYPE
import com.example.end_to_end_app.data.api.model.ApiToken
import com.example.end_to_end_app.data.preferences.Preferences
import com.google.gson.Gson
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.time.Instant

/**
 * this interceptor is used to
 * 1) add authentication headers
 * 2) refresh/manage the auth token
 * for each request from the client
 */
class AuthenticationInterceptor(
    private val preferences: Preferences
): Interceptor {

    companion object {
        const val UNAUTHORIZED = 401
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = preferences.getToken()
        val tokenExpirationTime = Instant.ofEpochSecond(preferences.getTokenExpirationTime())
        val request = chain.request()
        val interceptedRequest: Request
        if (tokenExpirationTime.isAfter(Instant.now())) {
            interceptedRequest = chain.createAuthenticatedRequest(token)
        } else {
            val tokenRefreshResponse = chain.refreshToken()
            interceptedRequest = if (tokenRefreshResponse.isSuccessful) {
                val newToken = mapToken(tokenRefreshResponse)
                if (newToken.isValid()) {
                    storeNewToken(newToken)
                    chain.createAuthenticatedRequest(newToken.accessToken!!)
                } else {
                    request
                }
            } else {
                request
            }
        }
        return chain.proceedDeletingTokenIfUnauthorized(interceptedRequest)
    }

    private fun Interceptor.Chain.createAuthenticatedRequest(token: String): Request {
        return request()
            .newBuilder()
            .addHeader(AUTH_HEADER, TOKEN_TYPE + token)
            .build()
    }

    private fun Interceptor.Chain.refreshToken(): Response {
        val url = request()
            .url
            .newBuilder(AUTH_ENDPOINT)!!
            .build()

        val body = FormBody.Builder()
            .add(GRANT_TYPE_KEY, GRANT_TYPE_VALUE)
            .add(CLIENT_ID, ApiConstants.KEY)
            .add(CLIENT_SECRET, ApiConstants.SECRET)
            .build()

        val tokenRefresh = request()
            .newBuilder()
            .post(body)
            .url(url)
            .build()

        return proceedDeletingTokenIfUnauthorized(tokenRefresh)
    }

    private fun Interceptor.Chain.proceedDeletingTokenIfUnauthorized(request: Request): Response {
        val response = proceed(request)

        if (response.code == UNAUTHORIZED) {
            preferences.deleteTokenInfo()
        }

        return response
    }


    private fun mapToken(tokenRefreshResponse: Response): ApiToken {
        val gson = Gson()
        val responseBody = tokenRefreshResponse.body!! // if successful, this should be good :]

        return gson.fromJson(responseBody.string(), ApiToken::class.java) ?: ApiToken.INVALID
    }


    private fun storeNewToken(apiToken: ApiToken) {
        with(preferences) {
            putTokenType(apiToken.tokenType!!)
            putTokenExpirationTime(apiToken.expiresAt)
            putToken(apiToken.accessToken!!)
        }
    }
}