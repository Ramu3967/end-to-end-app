
package com.example.pf_utils.model.mappers

interface UiMapper<E, V> {

  fun mapToView(input: E): V
}