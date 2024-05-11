
package com.example.end_to_end_app.data.api.model.mappers

import com.example.end_to_end_app.common.domain.model.animal.Media
import com.example.end_to_end_app.data.api.model.ApiPhotoSizes
import javax.inject.Inject


class ApiPhotoMapper  @Inject constructor() : ApiMapper<ApiPhotoSizes?, Media.Photo> {

  override fun mapToDomain(apiEntity: ApiPhotoSizes?): Media.Photo {
    return Media.Photo(
        medium = apiEntity?.medium.orEmpty(),
        full = apiEntity?.full.orEmpty()
    )
  }
}
