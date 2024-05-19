package com.example.end_to_end_app.common.data.preferences

import com.example.end_to_end_app.common.data.preferences.ConstantsPreferences.KEY_TOKEN
import com.example.end_to_end_app.common.data.preferences.ConstantsPreferences.KEY_TOKEN_EXPIRATION_TIME
import com.example.end_to_end_app.common.data.preferences.ConstantsPreferences.KEY_TOKEN_TYPE

// used by the auth interceptor
class FakePreferences: IPreferences {
    private val preferences = mutableMapOf<String, Any>()

    override fun putToken(token: String) {
        preferences[KEY_TOKEN] = token
    }

    override fun putTokenExpirationTime(time: Long) {
        preferences[KEY_TOKEN_EXPIRATION_TIME] = time
    }

    override fun putTokenType(tokenType: String) {
        preferences[KEY_TOKEN_TYPE] = tokenType
    }

    override fun getToken(): String {
        return preferences[KEY_TOKEN] as String
    }

    override fun getTokenExpirationTime(): Long {
        return preferences[KEY_TOKEN_EXPIRATION_TIME] as Long
    }

    override fun getTokenType(): String {
        return preferences[KEY_TOKEN_TYPE] as String
    }

    override fun deleteTokenInfo() {
        preferences.clear()
    }
}