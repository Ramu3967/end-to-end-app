package com.example.end_to_end_app.common.data.api.utils

import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import com.example.end_to_end_app.common.data.api.ApiConstants
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import java.io.IOException
import java.io.InputStream

class FakeServer {
    private val mockWebServer = MockWebServer()

    private val notFoundResponse = MockResponse().setResponseCode(404)
    private val animalsEndpointPath = "/"+ ApiConstants.ANIMALS_ENDPOINT

    val baseEndpoint
        get() = mockWebServer.url("/")

    fun start() = mockWebServer.start(8080)
    fun shutdown() = mockWebServer.shutdown()


    fun setHappyPathDispatcher() {
        mockWebServer.dispatcher = object : Dispatcher() {
            override fun dispatch(request: RecordedRequest): MockResponse {
                val path = request.path ?: return notFoundResponse

                return with(path) {
                    when {
                        startsWith(animalsEndpointPath) -> {
                            MockResponse().setResponseCode(200).setBody(getJson("mock_animals.json"))
                        }
                        else -> {
                            notFoundResponse
                        }
                    }
                }
            }
        }
    }

    private fun getJson(path: String): String {
        return try {
            val context = InstrumentationRegistry.getInstrumentation().context
            val jsonStream: InputStream = context.assets.open("networkresponses/$path")
            String(jsonStream.readBytes())
        } catch (exception: IOException) {
            Log.e("FakeServerInTest", "Error reading network response json asset")
            throw exception
        }
    }
}