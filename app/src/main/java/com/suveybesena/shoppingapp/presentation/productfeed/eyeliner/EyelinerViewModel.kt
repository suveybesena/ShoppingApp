package com.suveybesena.shoppingapp.presentation.productfeed.eyeliner

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
class EyelinerViewModel @Inject constructor(
    var getAllProductItemsUseCase: GetAllProductItemsUseCase,
    val insertProductsUseCase: InsertProductsUseCase
) :
    ViewModel() {

    private val uiState = MutableLiveData<ProductFeedUiState>()
    var _uiState = uiState

    init {
        getEyelinerProducts("eyeliner")
    }

    fun getEyelinerProducts(categoryName: String) {

        viewModelScope.launch {
            getAllProductItemsUseCase.invoke(categoryName).collect { resource ->
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
                        uiState.value =
                            ProductFeedUiState(
                                productItems = null,
                                errorMessage = resource.message as String,
                                isLoading = false
                            )
                    }
                    is Resource.Loading -> {
                        uiState.value =
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

    fun insertData(product: MakeupItemResponseItem) {
        viewModelScope.launch {
            insertProductsUseCase.invoke(product).collect {  }
        }
    }


}