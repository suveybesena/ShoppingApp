package com.suveybesena.shoppingapp.presentation.productfeed.mainfeed

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suveybesena.shoppingapp.common.Resource
import com.suveybesena.shoppingapp.data.remote.model.MakeupItemsResponse
import com.suveybesena.shoppingapp.domain.uistate.ProductFeedUiState
import com.suveybesena.shoppingapp.domain.usecase.GetAllProductItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductFeedViewModel @Inject constructor(var getAllProductItemsUseCase: GetAllProductItemsUseCase) :
    ViewModel() {

}