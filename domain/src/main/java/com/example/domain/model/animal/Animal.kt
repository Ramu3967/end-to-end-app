package com.example.domain.model.animal

import java.time.LocalDateTime

/**
 * The API response gives a huge JSON which is divided into custom types.
 * This model will be used for displaying in the main list.
 * API link - https://www.petfinder.com/developers/v2/docs/
 */
data class Animal(
    val id: Long,
    val name: String,
    val type: String,
    val media: Media,
    val tags: List<String>,
    val adoptionStatus: AdoptionStatus,
    val publishedAt: LocalDateTime
)
