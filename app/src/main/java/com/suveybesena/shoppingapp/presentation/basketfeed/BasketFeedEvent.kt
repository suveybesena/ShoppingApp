package com.suveybesena.shoppingapp.presentation.basketfeed

import com.suveybesena.shoppingapp.data.model.ProductFeatures

sealed class BasketFeedEvent {
    data class DeleteProduct(val product: ProductFeatures) : BasketFeedEvent()
    data class SaveProduct(val product: ProductFeatures) : BasketFeedEvent()
    object GetProductsFromLocal : BasketFeedEvent()
}
