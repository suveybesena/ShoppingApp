package com.suveybesena.shoppingapp.domain.repository

import com.suveybesena.shoppingapp.data.remote.MakeupItemsAPI
import javax.inject.Inject

class ShoppingRepository @Inject constructor(private val api : MakeupItemsAPI) {

    suspend fun getProducts(categoryName: String) =
        api.getProducts(categoryName)

}