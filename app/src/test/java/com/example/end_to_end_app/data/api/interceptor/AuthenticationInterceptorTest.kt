package com.example.end_to_end_app.data.api.interceptor

import com.example.end_to_end_app.data.api.ApiConstants
import com.example.end_to_end_app.data.preferences.Preferences
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class AuthenticationInterceptorTest{
    private lateinit var preferences: Preferences
    private lateinit var mockWebServer: MockWebServer
    private lateinit var okHttpClient: OkHttpClient
    //system under test
    private lateinit var sut: AuthenticationInterceptor

    private val endpointSeparator = "/"
    private val animalsEndpointPath = endpointSeparator + ApiConstants.ANIMALS_ENDPOINT
    private val authEndpointPath = endpointSeparator + ApiConstants.AUTH_ENDPOINT
    private val validToken = "validToken"
    private val expiredToken = "expiredToken"

    @Before
    fun setup() {
        preferences = mock(Preferences::class.java)
        mockWebServer = MockWebServer()
        mockWebServer.start(8080)
        sut = AuthenticationInterceptor(preferences)
        okHttpClient = OkHttpClient().newBuilder()
            .addInterceptor(sut).build()
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }
}