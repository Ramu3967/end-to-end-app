package com.example.end_to_end_app.common.data.api.utils

import com.example.end_to_end_app.common.data.api.ApiConstants
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

class FakeServer {
    private val mockWebServer = MockWebServer()

    private val notFoundResponse = MockResponse().setResponseCode(404)
    private val animalsEndpointPath = "/"+ ApiConstants.ANIMALS_ENDPOINT

    val baseEndpoint
        get() = mockWebServer.url("/")

    fun start() = mockWebServer.start(8080)
    fun shutdown() = mockWebServer.shutdown()

    // need to add a dispatcher that returns responses from the assets folder
}