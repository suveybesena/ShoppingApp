package com.suveybesena.shoppingapp.data.local

import androidx.room.*
import com.suveybesena.shoppingapp.data.model.ProductFeatures

@Dao
interface ProductDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductFeatures): Long

    @Query("SELECT * FROM products")
    fun getAllProducts(): MutableList<ProductFeatures>

    @Delete
    suspend fun deleteProduct(product: ProductFeatures)

}