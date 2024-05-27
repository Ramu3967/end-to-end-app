
package com.example.data.api.model.mappers

import com.example.data.api.model.ApiAddress
import com.example.domain.model.organization.Organization
import javax.inject.Inject

/**
 * These mappers are used to obtain the domain entities from the Api entities.
 * But we employ AnimalRepository (soon) to invert this dependency between domain and data layers.
 */
class ApiAddressMapper  @Inject constructor(): ApiMapper<ApiAddress?, Organization.Address> {

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