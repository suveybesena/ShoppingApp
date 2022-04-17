package com.suveybesena.shoppingapp.presentation.basketfeed

import com.suveybesena.shoppingapp.data.model.ProductFeatures

data class BasketFeedUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val basketFeedItems: List<ProductFeatures>? = null
)