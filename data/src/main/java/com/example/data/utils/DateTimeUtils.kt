
package com.example.data.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateTimeUtils {
  fun parse(dateTimeString: String): LocalDateTime = try {
      LocalDateTime.parse(dateTimeString)
    } catch (e: Exception) {
      val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ")
      LocalDateTime.parse(dateTimeString, dateFormatter)
    }
}
