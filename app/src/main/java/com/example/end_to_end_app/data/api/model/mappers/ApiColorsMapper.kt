
package com.example.end_to_end_app.data.api.model.mappers

import com.example.end_to_end_app.common.domain.model.animal.details.Colors
import com.example.end_to_end_app.data.api.model.ApiColors


class ApiColorsMapper : ApiMapper<ApiColors?, Colors> {

  override fun mapToDomain(apiEntity: ApiColors?): Colors {
    return Colors(
        primary = apiEntity?.primary.orEmpty(),
        secondary = apiEntity?.secondary.orEmpty(),
        tertiary = apiEntity?.tertiary.orEmpty()
    )
  }
}
