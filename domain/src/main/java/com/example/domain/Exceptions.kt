package com.example.domain

import java.io.IOException

class NetworkUnavailableException(message: String = "No network available :(") : IOException(message)

class NoMoreAnimalsException(message: String): Exception(message)