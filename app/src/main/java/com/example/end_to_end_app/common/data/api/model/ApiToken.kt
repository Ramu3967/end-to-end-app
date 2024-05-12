package com.example.end_to_end_app.common.data.api.model

import com.google.gson.annotations.SerializedName
import java.time.Instant

data class ApiToken(
    @SerializedName("token_type") val tokenType: String?,
    @SerializedName("expires_in") val expiresInSeconds: Int?,
    @SerializedName("access_token") val accessToken: String?
) {
    // using these extra util functions to make our work easy
    companion object {
        val INVALID = ApiToken("", -1, "")
    }

    @Transient
    private val requestedAt: Instant = Instant.now()

    val expiresAt: Long
        get() {
            if (expiresInSeconds == null) return 0L

            return requestedAt.plusSeconds(expiresInSeconds.toLong()).epochSecond
        }

    fun isValid(): Boolean {
        return !tokenType.isNullOrEmpty() &&
                expiresInSeconds != null && expiresInSeconds >= 0 &&
                !accessToken.isNullOrEmpty()
    }
}
