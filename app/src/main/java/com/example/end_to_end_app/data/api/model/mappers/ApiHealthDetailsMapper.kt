
package com.example.end_to_end_app.data.api.model.mappers

import com.example.end_to_end_app.common.domain.model.animal.details.HealthDetails
import com.example.end_to_end_app.data.api.model.ApiAttributes
import javax.inject.Inject


class ApiHealthDetailsMapper  @Inject constructor() :
    ApiMapper<ApiAttributes?, HealthDetails> {

  override fun mapToDomain(apiEntity: ApiAttributes?): HealthDetails {
    return HealthDetails(
        isSpayedOrNeutered = apiEntity?.spayedNeutered ?: false,
        isDeclawed = apiEntity?.declawed ?: false,
        hasSpecialNeeds = apiEntity?.specialNeeds ?: false,
        shotsAreCurrent = apiEntity?.shotsCurrent ?: false
    )
  }
}
