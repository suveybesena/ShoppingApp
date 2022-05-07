package com.suveybesena.shoppingapp.data.repository

import com.suveybesena.shoppingapp.common.Constants
import com.suveybesena.shoppingapp.data.local.ProductDAO
import com.suveybesena.shoppingapp.data.model.ProductFeatures
import com.suveybesena.shoppingapp.data.remote.ProductAPI
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ShoppingRepository @Inject constructor(
    private val remoteDataSource: ProductAPI,
    private val localDataSource: ProductDAO
) {

    suspend fun insertProduct(product: ProductFeatures) = localDataSource.insertProduct(product)

    suspend fun getLipstickProducts() = remoteDataSource.getProducts(Constants.LIPSTICK)

    suspend fun getEyelinerProducts() = remoteDataSource.getProducts(Constants.EYELINER)

    suspend fun getEyeshadowProducts() = remoteDataSource.getProducts(Constants.EYESHADOW)

    fun getAllProductsFromLocal() = localDataSource.getAllProducts()

    suspend fun deleteProduct(product: ProductFeatures) = localDataSource.deleteProduct(product)

}