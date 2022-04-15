package com.suveybesena.shoppingapp.domain.repository

import com.suveybesena.shoppingapp.data.local.ProductDAO
import com.suveybesena.shoppingapp.data.remote.MakeupItemsAPI
import com.suveybesena.shoppingapp.data.remote.model.MakeupItemResponseItem
import javax.inject.Inject

class ShoppingRepository @Inject constructor(
    private val api: MakeupItemsAPI,
    private val db: ProductDAO
) {
    suspend fun getProducts(categoryName: String) =
        api.getProducts(categoryName)

    suspend fun insert (product : MakeupItemResponseItem)= db.insertProduct(product)

    fun getAllProducts ()= db.getAllProducts()


    suspend fun delete (product: MakeupItemResponseItem) = db.deleteProducts(product)

}