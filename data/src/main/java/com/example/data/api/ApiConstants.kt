package com.example.data.api

object ApiConstants {
    const val BASE_ENDPOINT = "https://api.petfinder.com/v2/"
    const val AUTH_ENDPOINT = "oauth2/token/"
    const val ANIMALS_ENDPOINT = "animals"

    const val KEY = "tL4Z9TRz96u0vPYL2ky82ls1I07M5zgY9Q55dxW5Daudjq0v2A"
    const val SECRET = "vbiFM2G06gVITuODqdgiIbu7t57IGKsZ7sTnxcne"
}

object ApiParameters {
    const val PAGE = "page"
    const val LIMIT = "limit"
    const val LOCATION = "location"
    const val DISTANCE = "distance"
    const val NAME = "name"
    const val AGE = "age"
    const val TYPE = "type"


    const val TOKEN_TYPE = "Bearer "
    const val AUTH_HEADER = "Authorization"
    const val GRANT_TYPE_KEY = "grant_type"
    const val GRANT_TYPE_VALUE = "client_credentials"
    const val CLIENT_ID = "client_id"
    const val CLIENT_SECRET = "client_secret"
}