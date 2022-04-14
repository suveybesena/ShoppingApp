package com.suveybesena.shoppingapp.common

sealed class Resources<T>(
    val data: T? = null,
    val message: String? = null,
) {
    class success<T>(data: T) : Resources<T>(data)
    class error<T>(message: String, data: T? = null) : Resources<T>(data, message)
    class loading<T> : Resources<T>()


}