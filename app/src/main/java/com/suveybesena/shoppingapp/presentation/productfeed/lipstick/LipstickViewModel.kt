package com.suveybesena.shoppingapp.presentation.productfeed.lipstick

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suveybesena.shoppingapp.common.Resource
import com.suveybesena.shoppingapp.data.remote.model.MakeupItemResponseItem
import com.suveybesena.shoppingapp.data.remote.model.MakeupItemsResponse
import com.suveybesena.shoppingapp.domain.uistate.ProductFeedUiState
import com.suveybesena.shoppingapp.domain.usecase.GetAllProductItemsUseCase
import com.suveybesena.shoppingapp.domain.usecase.InsertProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LipstickViewModel @Inject constructor(
    val getProductItemUseCase: GetAllProductItemsUseCase,
    val insertProductsUseCase: InsertProductsUseCase
) : ViewModel() {

    private val uiState = MutableLiveData<ProductFeedUiState>()
    var _uiState = uiState

    init {
        getlipstickProducts("lipstick")
    }

    fun getlipstickProducts(categoryName: String) {
        viewModelScope.launch {
            getProductItemUseCase.invoke(categoryName).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        uiState.value = ProductFeedUiState(
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

    fun insertData(products: MakeupItemResponseItem) =
        viewModelScope.launch {
            insertProductsUseCase.invoke(products).collect {  }
        }


}