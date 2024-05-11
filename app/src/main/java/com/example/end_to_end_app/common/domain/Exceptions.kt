package com.example.end_to_end_app.common.domain

import java.io.IOException

class NetworkUnavailableException(message: String = "No network available :(") : IOException(message)