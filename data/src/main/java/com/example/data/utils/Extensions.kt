package com.example.data.utils

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun CoroutineScope.createExceptionHandler(
    message: String,
    action: (throwable: Throwable) -> Unit
)= CoroutineExceptionHandler { coroutineContext, throwable ->
    Log.e(this.coroutineContext.toString(), message)
    launch {
        action(throwable)
    }
}