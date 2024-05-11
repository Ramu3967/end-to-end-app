
package com.example.end_to_end_app.common.domain.model.animal.details

import com.example.end_to_end_app.common.domain.model.organization.Organization


data class Details(
    val description: String,
    val age: Age,
    val species: String,
    val breed: Breed,
    val colors: Colors,
    val gender: Gender,
    val size: Size,
    val coat: Coat,
    val healthDetails: HealthDetails,
    val habitatAdaptation: HabitatAdaptation,
    val organization: Organization
)