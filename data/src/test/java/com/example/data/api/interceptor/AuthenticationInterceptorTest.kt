package com.example.data.api.interceptor

import com.example.data.api.ApiConstants
import com.example.data.api.ApiParameters
import com.example.data.preferences.IPreferences
import com.google.common.truth.Truth.assertThat
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import java.time.Instant

class AuthenticationInterceptorTest{
    private lateinit var preferences: IPreferences
    private lateinit var mockWebServer: MockWebServer
    private lateinit var okHttpClient: OkHttpClient
    //system under test
    private lateinit var sut: AuthenticationInterceptor

    private val endpointSeparator = "/"
    private val animalsEndpointPath = endpointSeparator + ApiConstants.ANIMALS_ENDPOINT
    private val validToken = "validToken"


    @Before
    fun setup() {
        preferences = mock(IPreferences::class.java)
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

    @Test
    fun authenticationInterceptor_validToken() {
        // Given
        Mockito.`when`(preferences.getToken()).thenReturn(validToken)
        Mockito.`when`(preferences.getTokenExpirationTime()).thenReturn(
            Instant.now().plusSeconds(3600).epochSecond
        )
        mockWebServer.dispatcher = getDispatcherForValidToken()
        // When
        okHttpClient.newCall(
            Request.Builder()
                .url(mockWebServer.url(ApiConstants.ANIMALS_ENDPOINT))
                .build())
            .execute()
        // Then
        val request = mockWebServer.takeRequest()
        request.run {
            assertThat(method).isEqualTo("GET")
            assertThat(path).isEqualTo(animalsEndpointPath)
            assertThat(getHeader(ApiParameters.AUTH_HEADER))
                .isEqualTo(ApiParameters.TOKEN_TYPE + validToken)

        }
    }


    private fun getDispatcherForValidToken() = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                animalsEndpointPath -> MockResponse().setResponseCode(200)
                else -> MockResponse().setResponseCode(404)
            }
        }
    }

}