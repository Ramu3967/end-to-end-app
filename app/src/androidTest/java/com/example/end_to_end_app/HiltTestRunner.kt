package com.example.end_to_end_app

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

/**
 * 1) AndroidRunners are needed for executing code in Android environments and not on JVMs. These tests
 * run on the emulator/device and are a bit slow.
 * 2) Hilt for testing needs an application object (from the HiltTestApplication). Provide the same
 * to the below runner.
 */
class HiltTestRunner: AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}