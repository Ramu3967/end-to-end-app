
package com.example.end_to_end_app.data.api.model.mappers

interface ApiMapper<E, D> {

  fun mapToDomain(apiEntity: E): D
}