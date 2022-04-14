package com.suveybesena.shoppingapp.data.remote.model


import com.google.gson.annotations.SerializedName

data class MakeupItemResponseItem(
    @SerializedName("api_featured_image")
    val apiFeaturedİmage: String,
    @SerializedName("brand")
    val brand: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("currency")
    val currency: Any,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_link")
    val imageLink: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: String,
    @SerializedName("price_sign")
    val priceSign: Any,
    @SerializedName("product_api_url")
    val productApiUrl: String,
    @SerializedName("product_colors")
    val productColors: List<ProductColor>,
    @SerializedName("product_link")
    val productLink: String,
    @SerializedName("product_type")
    val productType: String,
    @SerializedName("rating")
    val rating: Double,
    @SerializedName("tag_list")
    val tagList: List<Any>,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("website_link")
    val websiteLink: String
)