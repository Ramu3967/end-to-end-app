
package com.example.end_to_end_app.data.api.model.mappers

import com.example.end_to_end_app.common.domain.model.animal.details.HabitatAdaptation
import com.example.end_to_end_app.data.api.model.ApiEnvironment


class ApiHabitatAdaptationMapper :
    ApiMapper<ApiEnvironment?, HabitatAdaptation> {

  override fun mapToDomain(apiEntity: ApiEnvironment?): HabitatAdaptation {
    return HabitatAdaptation(
        goodWithChildren = apiEntity?.children ?: true,
        goodWithDogs = apiEntity?.dogs ?: true,
        goodWithCats = apiEntity?.cats ?: true
    )
  }
}
