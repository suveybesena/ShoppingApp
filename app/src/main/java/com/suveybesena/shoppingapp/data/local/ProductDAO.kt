package com.suveybesena.shoppingapp.data.local


import androidx.lifecycle.LiveData
import androidx.room.*
import com.suveybesena.shoppingapp.data.remote.model.MakeupItemResponseItem
@Dao
interface ProductDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct (product : MakeupItemResponseItem) :Long

    @Query("SELECT * FROM products")
     fun getAllProducts() : List<MakeupItemResponseItem>

     @Delete
     suspend fun deleteProducts(products: MakeupItemResponseItem)

}