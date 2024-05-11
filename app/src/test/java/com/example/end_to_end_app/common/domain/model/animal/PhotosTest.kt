package com.example.end_to_end_app.common.domain.model.animal

import org.junit.Assert.*
import org.junit.Test

// Unit tests
class PhotosTest{
    private val mediumPhoto = "mediumPhoto"
    private val fullPhoto = "fullPhoto"
    private val invalidPhoto = "" // what's tested in Photo.isValidPhoto()

    @Test
    fun photo_getSmallestAvailablePhoto_hasMediumPhoto() {
        // Given
        val photo = Media.Photo(mediumPhoto, fullPhoto)
        val expectedValue = mediumPhoto

        // When
        val smallestPhoto = photo.getSmallestAvailablePhoto()

        // Then
        assertEquals(smallestPhoto, expectedValue)
    }

    @Test
    fun photo_getSmallestAvailablePhoto_noMediumPhoto_hasFullPhoto() {
        // Given
        val photo = Media.Photo(invalidPhoto, fullPhoto)
        val expectedValue = fullPhoto

        // When
        val smallestPhoto = photo.getSmallestAvailablePhoto()

        // Then
        assertEquals(smallestPhoto, expectedValue)
    }

    @Test
    fun photo_getSmallestAvailablePhoto_noPhotos() {
        // Given
        val photo = Media.Photo(invalidPhoto, invalidPhoto)
        val expectedValue = Media.Photo.NO_SIZE_AVAILABLE

        // When
        val smallestPhoto = photo.getSmallestAvailablePhoto()

        // Then
        assertEquals(smallestPhoto, expectedValue)
    }
}