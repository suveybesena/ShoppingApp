package com.suveybesena.shoppingapp.presentation.mainfeed.lipstick

import com.suveybesena.shoppingapp.data.model.ProductFeatures

data class LipstickFeedUiState(
    val error: String? = null,
    val isLoading: Boolean = false,
    val lipstickItems : List<ProductFeatures>? = null
)