
package com.example.end_to_end_app.data.api.model.mappers

import com.example.end_to_end_app.common.domain.model.organization.Organization
import com.example.end_to_end_app.data.api.model.ApiAddress

/**
 * These mappers are used to obtain the domain entities from the Api entities.
 * But we employ AnimalRepository (soon) to invert this dependency between domain and data layers.
 */
class ApiAddressMapper : ApiMapper<ApiAddress?, Organization.Address> {

  override fun mapToDomain(apiEntity: ApiAddress?): Organization.Address {
    return Organization.Address(
        address1 = apiEntity?.address1.orEmpty(),
        address2 = apiEntity?.address2.orEmpty(),
        city = apiEntity?.city.orEmpty(),
        state = apiEntity?.state.orEmpty(),
        postcode = apiEntity?.postcode.orEmpty(),
        country = apiEntity?.country.orEmpty()
    )
  }
}