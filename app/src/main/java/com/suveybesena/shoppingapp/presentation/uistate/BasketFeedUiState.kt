package com.suveybesena.shoppingapp.presentation.uistate

import com.suveybesena.shoppingapp.presentation.productfeed.main.ProductFeatures

data class BasketFeedUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val basketFeedItems: List<ProductFeatures>? = null
)