package com.suveybesena.shoppingapp.presentation.mainfeed.lipstick

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suveybesena.shoppingapp.common.Resource
import com.suveybesena.shoppingapp.domain.usecase.InsertProductToLocalUseCase
import com.suveybesena.shoppingapp.data.model.ProductFeatures
import com.suveybesena.shoppingapp.domain.usecase.GetLipstickProductsFromRemoteUseCase
import com.suveybesena.shoppingapp.presentation.mainfeed.MainFeedEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LipstickViewModel @Inject constructor(
    private val getLipstickProductsUseCase: GetLipstickProductsFromRemoteUseCase,
    private val insertProductUseCase: InsertProductToLocalUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LipstickFeedUiState())
    var uiState: StateFlow<LipstickFeedUiState> = _uiState.asStateFlow()

    fun handleEvent(event: MainFeedEvent) {
        when (event) {
            is MainFeedEvent.SaveMainToLocalDatabase -> {
                saveProductToLocalDb(event.product)
            }
            is MainFeedEvent.GetMain -> {
                getLipstickProducts()
            }
        }
    }

    private fun getLipstickProducts() {
        viewModelScope.launch {
            getLipstickProductsUseCase.invoke().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        _uiState.update { state ->
                            state.copy(lipstickItems = resource.data as List<ProductFeatures>)
                        }
                    }
                    is Resource.Error -> {
                        _uiState.update { state ->
                            state.copy(error = resource.message as String)

                        }
                    }
                    is Resource.Loading -> {
                        _uiState.update {
                            it.copy(isLoading = true)
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