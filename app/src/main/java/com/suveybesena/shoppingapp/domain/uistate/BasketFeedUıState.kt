package com.suveybesena.shoppingapp.domain.uistate

import com.suveybesena.shoppingapp.data.remote.model.MakeupItemResponseItem

data class BasketFeedUıState(
    val isLoading : Boolean = false,
    val error : String? = null,
    val basketFeedItems : List<MakeupItemResponseItem>?
) {
}