package com.suveybesena.shoppingapp.presentation.productfeed

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

    private val uiState = MutableLiveData<ProductFeedUiState>()
    val _uiState = uiState

    init {
        getProducts("maybelline")
    }

    fun getProducts(brandName: String) {
        viewModelScope.launch {
            getAllProductItemsUseCase.invoke(brandName).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        uiState.value =
                            ProductFeedUiState(
                                productItems = resource.data as MakeupItemsResponse,
                                errorMessage = null,
                                isLoading = false
                            )
                    }
                    is Resource.Error -> {
                        ProductFeedUiState(
                            productItems = null,
                            errorMessage = resource.message as String,
                            isLoading = false
                        )
                    }

                    is Resource.Loading -> {
                        ProductFeedUiState(
                            productItems = null,
                            errorMessage = null,
                            isLoading = true
                        )
                    }
                }

            }
        }

    }

}