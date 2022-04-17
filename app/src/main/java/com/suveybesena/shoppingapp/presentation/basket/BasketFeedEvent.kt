package com.suveybesena.shoppingapp.presentation.basket

import com.suveybesena.shoppingapp.presentation.productfeed.main.ProductFeatures

sealed class BasketFeedEvent {
    data class DeleteProduct(val product: ProductFeatures) : BasketFeedEvent()
    data class SaveProduct(val product: ProductFeatures) : BasketFeedEvent()
    object GetProductsFromLocal : BasketFeedEvent()
}
