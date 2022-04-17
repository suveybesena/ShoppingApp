package com.suveybesena.shoppingapp.presentation.productfeed.main



sealed class ProductFeedEvent {
    data class SaveProductToLocalDatabase(val product: ProductFeatures) : ProductFeedEvent()
    data class GetProduct(val categoryName: String) : ProductFeedEvent()

}
