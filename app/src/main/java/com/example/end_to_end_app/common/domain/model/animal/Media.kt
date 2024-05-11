package com.example.end_to_end_app.common.domain.model.animal

/**
 * The API response is divided into cohesive classes (working towards one common goal).
 */
data class Media(
    val photos: List<Photo>,
    val videos: List<Video>
) {

    data class Photo(
        val medium: String,
        val full: String
    ) {

        companion object {
            const val NO_SIZE_AVAILABLE = ""
        }

        // to display in the list
        fun getSmallestAvailablePhoto(): String {
            return when {
                isValidPhoto(medium) -> medium
                isValidPhoto(full) -> full
                else -> NO_SIZE_AVAILABLE
            }
        }

        private fun isValidPhoto(photo: String): Boolean {
            return photo.isNotEmpty()
        }
    }

    // videos will be implemented later
    data class Video(val video: String)
}