
package com.example.end_to_end_app.common.presentation.model.mappers

interface UiMapper<E, V> {

  fun mapToView(input: E): V
}