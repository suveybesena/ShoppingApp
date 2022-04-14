package com.suveybesena.shoppingapp.common

sealed class Resource<T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error<T>(val message: T) : Resource<T>()
    object Loading : Resource<Nothing>()
}