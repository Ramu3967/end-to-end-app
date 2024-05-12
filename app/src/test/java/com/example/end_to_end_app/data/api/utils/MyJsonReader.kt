package com.example.end_to_end_app.data.api.utils

import android.util.Log
import androidx.test.platform.app.InstrumentationRegistry
import java.io.IOException
import java.io.InputStream

object JsonReader {
    fun getJson(path: String): String {
        return try {
            val context = InstrumentationRegistry.getInstrumentation().context
            val jsonStream: InputStream = context.assets.open(path)
            String(jsonStream.readBytes())
        } catch (exception: IOException) {
            Log.e("JsonReaderLogMsg", "Error reading network response json asset${exception.message}")
            throw exception
        }
    }
}