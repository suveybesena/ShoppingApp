package com.suveybesena.shoppingapp.presentation.productfeed.eyeshadow

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
class EyeShadowViewModel @Inject constructor(
    val getAllProductItemsUseCase: GetAllProductItemsUseCase,
    val insertProductsUseCase: InsertProductsUseCase
) : ViewModel() {

    private val uiState = MutableLiveData<ProductFeedUiState>()
    var _uiState = uiState

    init {
        getEyeshadowItem("eyeshadow")
    }

    fun getEyeshadowItem(categoryName: String) {

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
                    is Resource.Loading -> {
                        ProductFeedUiState(
                            productItems = null,
                            errorMessage = null,
                            isLoading = true
                        )
                    }
                    is Resource.Error -> {
                        ProductFeedUiState(
                            productItems = null,
                            errorMessage = resource.message as String,
                            isLoading = false
                        )
                    }
                }

            }

        }


    }
    fun insertProduct(products : MakeupItemResponseItem){
        viewModelScope.launch {
            insertProductsUseCase.invoke(products).collect {  }
        }
    }



}