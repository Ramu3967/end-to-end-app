package com.example.end_to_end_app.data.preferences

interface Preferences {

    fun putToken(token: String)

    fun putTokenExpirationTime(time: Long)

    fun putTokenType(tokenType: String)

    fun getToken(): String

    fun getTokenExpirationTime(): Long

    fun getTokenType(): String

    fun deleteTokenInfo()
}

