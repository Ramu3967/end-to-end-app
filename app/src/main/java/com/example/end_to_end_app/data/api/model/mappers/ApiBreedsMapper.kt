
package com.example.end_to_end_app.data.api.model.mappers

import com.example.end_to_end_app.common.domain.model.animal.details.Breed
import com.example.end_to_end_app.data.api.model.ApiBreeds


class ApiBreedsMapper : ApiMapper<ApiBreeds?, Breed> {

  override fun mapToDomain(apiEntity: ApiBreeds?): Breed {
    return Breed(
        primary = apiEntity?.primary.orEmpty(),
        secondary = apiEntity?.secondary.orEmpty()
    )
  }
}