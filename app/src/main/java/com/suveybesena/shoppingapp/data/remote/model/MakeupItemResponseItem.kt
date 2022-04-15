package com.suveybesena.shoppingapp.data.remote.model


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity (
    tableName = "products"
        )
data class MakeupItemResponseItem(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name ="id")
    @SerializedName("id")
    val id: Int?,
    @ColumnInfo(name ="api_featured_image")
    @SerializedName("api_featured_image")
    val apiFeaturedİmage: String?,
    @ColumnInfo(name ="brand")
    @SerializedName("brand")
    val brand: String?,
    @ColumnInfo(name ="category")
    @SerializedName("category")
    val category: String?,
    @ColumnInfo(name ="created_at")
    @SerializedName("created_at")
    val createdAt: String?,
    @ColumnInfo(name ="description")
    @SerializedName("description")
    val description: String?,
    @ColumnInfo(name ="image_link")
    @SerializedName("image_link")
    val imageLink: String?,
    @ColumnInfo(name ="name")
    @SerializedName("name")
    val name: String?,
    @ColumnInfo(name ="price")
    @SerializedName("price")
    val price: String?,
    @ColumnInfo(name ="product_api_url")
    @SerializedName("product_api_url")
    val productApiUrl: String?,
    @ColumnInfo(name ="product_link")
    @SerializedName("product_link")
    val productLink: String?,
    @ColumnInfo(name ="product_type")
    @SerializedName("product_type")
    val productType: String?,
    @ColumnInfo(name ="rating")
    @SerializedName("rating")
    val rating: Double?,
    @ColumnInfo(name ="updated_at")
    @SerializedName("updated_at")
    val updatedAt: String?,
    @ColumnInfo(name ="website_link")
    @SerializedName("website_link")
    val websiteLink: String?
)