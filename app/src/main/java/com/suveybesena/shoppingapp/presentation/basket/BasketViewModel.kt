package com.suveybesena.shoppingapp.presentation.basket

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suveybesena.shoppingapp.common.Resource
import com.suveybesena.shoppingapp.data.remote.model.MakeupItemResponseItem
import com.suveybesena.shoppingapp.data.remote.model.MakeupItemsResponse
import com.suveybesena.shoppingapp.domain.uistate.BasketFeedUıState
import com.suveybesena.shoppingapp.domain.uistate.ProductFeedUiState
import com.suveybesena.shoppingapp.domain.usecase.GetProductsFromLocalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor(val getDataFromLocalUseCase: GetProductsFromLocalUseCase) :
    ViewModel() {

    private val uiState = MutableLiveData<BasketFeedUıState>()
    var _uiState = uiState

    fun getAllProducts() {
        viewModelScope.launch {
            getDataFromLocalUseCase.invoke().collect { resource ->

                when (resource) {
                    is Resource.Success -> {

                        uiState.value = BasketFeedUıState(
                            basketFeedItems = resource.data as List<MakeupItemResponseItem>,
                            error = null,
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {

                        BasketFeedUıState(
                            basketFeedItems = null,
                            error = resource.message as String,
                            isLoading = false

                        )
                    }
                    is Resource.Loading -> {
                        BasketFeedUıState(
                            basketFeedItems = null,
                            error = null,
                            isLoading = true
                        )
                    }
                }
            }


        }
    }


}