package com.suveybesena.shoppingapp.data.model

import com.google.gson.annotations.SerializedName


data class MakeupItemResponseItem(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("api_featured_image")
    val apiFeaturedİmage: String?,
    @SerializedName("brand")
    val brand: String?,
    @SerializedName("category")
    val category: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("image_link")
    val imageLink: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("price")
    val price: String?,
    @SerializedName("product_api_url")
    val productApiUrl: String?,
    @SerializedName("product_link")
    val productLink: String?,
    @SerializedName("product_type")
    val productType: String?,
    @SerializedName("rating")
    val rating: Double?,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("website_link")
    val websiteLink: String?
)

fun MakeupItemResponseItem.toProductFeatures() = ProductFeatures(
    productId = id,
    productName = brand,
    productLink = productLink,
    productImage = imageLink,
    productCategory = category,
    productPrice = price
)