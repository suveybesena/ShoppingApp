package com.suveybesena.shoppingapp.presentation.mainfeed

import com.suveybesena.shoppingapp.data.model.ProductFeatures


sealed class MainFeedEvent {
    data class SaveMainToLocalDatabase(val product: ProductFeatures) : MainFeedEvent()
    object GetMain : MainFeedEvent()
}
