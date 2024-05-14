package com.example.end_to_end_app.data.api.interceptor

import com.example.end_to_end_app.common.data.api.interceptor.AuthenticationInterceptor
import com.example.end_to_end_app.common.data.preferences.IPreferences
import com.example.end_to_end_app.data.api.utils.JsonReader
import com.google.common.truth.Truth.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.robolectric.RobolectricTestRunner
import java.time.Instant

@RunWith(RobolectricTestRunner::class)
class AuthenticationInterceptorTest{
    private lateinit var preferences: IPreferences
    private lateinit var mockWebServer: MockWebServer
    private lateinit var okHttpClient: OkHttpClient
    //system under test
    private lateinit var sut: AuthenticationInterceptor

    private val endpointSeparator = "/"
    private val animalsEndpointPath = endpointSeparator + com.example.end_to_end_app.common.data.api.ApiConstants.ANIMALS_ENDPOINT
    private val validToken = "validToken"

    private val authEndpointPath = endpointSeparator + com.example.end_to_end_app.common.data.api.ApiConstants.AUTH_ENDPOINT
    private val expiredToken = "expiredToken"

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
                .url(mockWebServer.url(com.example.end_to_end_app.common.data.api.ApiConstants.ANIMALS_ENDPOINT))
                .build())
            .execute()
        // Then
        val request = mockWebServer.takeRequest()
        request.run {
            assertThat(method).isEqualTo("GET")
            assertThat(path).isEqualTo(animalsEndpointPath)
            assertThat(getHeader(com.example.end_to_end_app.common.data.api.ApiParameters.AUTH_HEADER))
                .isEqualTo(com.example.end_to_end_app.common.data.api.ApiParameters.TOKEN_TYPE + validToken)

        }
    }

    @Test
    fun authenticationInterceptor_expiredToken() {
        // Given
        Mockito.`when`(preferences.getToken()).thenReturn(expiredToken)
        Mockito.`when`(preferences.getTokenExpirationTime()).thenReturn(
            // minus = expired
            Instant.now().minusSeconds(3600).epochSecond
        )
        mockWebServer.dispatcher = getDispatcherForExpiredToken()
        // When
        okHttpClient.newCall(
            Request.Builder()
                .url(mockWebServer.url(com.example.end_to_end_app.common.data.api.ApiConstants.ANIMALS_ENDPOINT))
                .build())
            .execute()
        // Then
        val tokenRequest = mockWebServer.takeRequest()
        val animalsRequest = mockWebServer.takeRequest()
        tokenRequest.run {
            assertThat(method).isEqualTo("POST")
            assertThat(path).isEqualTo(authEndpointPath)
        }
        val inOrder = Mockito.inOrder(preferences)
        inOrder.verify(preferences).getToken()
        inOrder.verify(preferences).putToken(validToken)
        Mockito.verify(preferences, Mockito.times(1)).getToken() // 5
        Mockito.verify(preferences, Mockito.times(1)).putToken(validToken)
        Mockito.verify(preferences, Mockito.times(1)).getTokenExpirationTime()
        Mockito.verify(preferences, Mockito.times(1))
            .putTokenExpirationTime(ArgumentMatchers.anyLong())
        Mockito.verify(preferences, Mockito.times(1))
            .putTokenType(com.example.end_to_end_app.common.data.api.ApiParameters.TOKEN_TYPE.trim())
        Mockito.verifyNoMoreInteractions(preferences)
        with(animalsRequest) { // 6
            assertThat(method).isEqualTo("GET")
            assertThat(path).isEqualTo(animalsEndpointPath)
            assertThat(getHeader(com.example.end_to_end_app.common.data.api.ApiParameters.AUTH_HEADER))
                .isEqualTo(com.example.end_to_end_app.common.data.api.ApiParameters.TOKEN_TYPE + validToken)
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

    private fun getDispatcherForExpiredToken() = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.path) {
                authEndpointPath -> MockResponse()
                    .setResponseCode(200)
                    .setBody(JsonReader.getJson("networkresponses/validToken.json"))

                animalsEndpointPath -> MockResponse()
                    .setResponseCode(200)

                else -> MockResponse().setResponseCode(404)
            }
        }
    }
}