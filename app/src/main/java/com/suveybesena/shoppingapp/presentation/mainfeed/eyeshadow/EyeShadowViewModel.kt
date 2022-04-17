package com.suveybesena.shoppingapp.presentation.mainfeed.eyeshadow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suveybesena.shoppingapp.common.Resource
import com.suveybesena.shoppingapp.domain.usecase.InsertProductToLocalUseCase
import com.suveybesena.shoppingapp.data.model.ProductFeatures
import com.suveybesena.shoppingapp.domain.usecase.GetEyeshadowProductsFromRemoteUseCase
import com.suveybesena.shoppingapp.presentation.mainfeed.MainFeedEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EyeShadowViewModel @Inject constructor(
    private val getEyeshadowProductsUseCase: GetEyeshadowProductsFromRemoteUseCase,
    private val insertProductUseCase: InsertProductToLocalUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(EyeshadowFeedUiState())
    var uiState: StateFlow<EyeshadowFeedUiState> = _uiState.asStateFlow()

    fun handleEvent(event: MainFeedEvent) {
        when (event) {
            is MainFeedEvent.SaveMainToLocalDatabase -> {
                saveProductToLocalDb(event.product)
            }
            is MainFeedEvent.GetMain -> {
                getEyeshadowItem()
            }
        }
    }

    private fun getEyeshadowItem() {
        viewModelScope.launch {
            getEyeshadowProductsUseCase.invoke().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        _uiState.update { state ->
                            state.copy(eyeshadowItems = resource.data as List<ProductFeatures>)
                        }
                    }
                    is Resource.Loading -> {
                      _uiState.update { state ->
                        state.copy(isLoading = true)
                      }
                    }
                    is Resource.Error -> {
                        _uiState.update { state ->
                            state.copy(error = resource.message as String)
                        }
                    }
                }
            }
        }
    }

    private fun saveProductToLocalDb(product: ProductFeatures) {
        viewModelScope.launch {
            insertProductUseCase.invoke(product).collect { }
        }
    }
}