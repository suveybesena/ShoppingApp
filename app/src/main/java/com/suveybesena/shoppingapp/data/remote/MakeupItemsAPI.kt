package com.suveybesena.shoppingapp.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MakeupItemsAPI {

    @GET("api/v1/products.json")
    suspend fun getProducts(
        @Query("brand")
        brandName: String
    ): Response<MakeupItemsResponse>
}