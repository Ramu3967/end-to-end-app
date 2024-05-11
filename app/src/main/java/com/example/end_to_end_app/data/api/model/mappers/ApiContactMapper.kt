
package com.example.end_to_end_app.data.api.model.mappers

import com.example.end_to_end_app.common.domain.model.organization.Organization
import com.example.end_to_end_app.data.api.model.ApiContact


class ApiContactMapper (
    private val apiAddressMapper: ApiAddressMapper
): ApiMapper<ApiContact?, Organization.Contact> {

  override fun mapToDomain(apiEntity: ApiContact?): Organization.Contact {
    return Organization.Contact(
        email = apiEntity?.email.orEmpty(),
        phone = apiEntity?.phone.orEmpty(),
        address = apiAddressMapper.mapToDomain(apiEntity?.address)
    )
  }
}