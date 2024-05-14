package com.example.end_to_end_app.common.data.preferences

import android.content.Context
import com.example.end_to_end_app.common.data.preferences.ConstantsPreferences.KEY_TOKEN
import com.example.end_to_end_app.common.data.preferences.ConstantsPreferences.KEY_TOKEN_EXPIRATION_TIME
import com.example.end_to_end_app.common.data.preferences.ConstantsPreferences.KEY_TOKEN_TYPE
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PetFinderPreferences @Inject constructor(
    @ApplicationContext context: Context
) : IPreferences {

    companion object {
        const val PREFERENCES_NAME = "PET_SAVE_PREFERENCES"
    }

    private val preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)


    override fun putToken(token: String) {
        preferences.edit()
            .putString(KEY_TOKEN, token)
            .apply()
    }

    override fun putTokenExpirationTime(time: Long) {
        preferences.edit()
            .putLong(KEY_TOKEN_EXPIRATION_TIME, time)
            .apply()
    }

    override fun putTokenType(tokenType: String) {
        preferences.edit()
            .putString(KEY_TOKEN_TYPE, tokenType)
            .apply()
    }

    override fun getToken(): String =
        preferences.getString(KEY_TOKEN, "").orEmpty()

    override fun getTokenExpirationTime(): Long =
        preferences.getLong(KEY_TOKEN_EXPIRATION_TIME, -1)

    override fun getTokenType(): String =
        preferences.getString(KEY_TOKEN_TYPE, "").orEmpty()

    override fun deleteTokenInfo() {
        preferences.edit()
            .remove(KEY_TOKEN)
            .remove(KEY_TOKEN_TYPE)
            .remove(KEY_TOKEN_EXPIRATION_TIME)
            .apply()
    }
}