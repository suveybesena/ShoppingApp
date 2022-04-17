package com.suveybesena.shoppingapp.presentation.productfeed.main

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "products"
)
data class ProductFeatures(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "productId")
    @SerializedName("id")
    val productId : Int?,
    @SerializedName("brand")
    @ColumnInfo(name = "productName")
    val productName : String?,
    @SerializedName("product_link")
    @ColumnInfo(name = "productLink")
    val productLink : String?,
    @SerializedName("image_link")
    @ColumnInfo(name = "productImage")
    val productImage : String?,
    @SerializedName("category")
    @ColumnInfo(name = "productCategory")
    val productCategory : String?,
    @SerializedName("price")
    @ColumnInfo(name = "productPrice")
    val productPrice : String?,
)
