package com.suveybesena.shoppingapp.presentation.mainfeed.eyeshadow

import com.suveybesena.shoppingapp.data.model.ProductFeatures

data class EyeshadowFeedUiState(
    val error: String? = null,
    val isLoading: Boolean = false,
    val eyeshadowItems: List<ProductFeatures>? = null
)
