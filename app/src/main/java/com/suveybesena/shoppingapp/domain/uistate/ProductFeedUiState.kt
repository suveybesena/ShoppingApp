package com.suveybesena.shoppingapp.domain.uistate

import com.suveybesena.shoppingapp.data.remote.model.MakeupItemsResponse

data class ProductFeedUiState(
    val isLoading: Boolean=false,
    val errorMessage:String?=null,
    val productItems : MakeupItemsResponse?
)
