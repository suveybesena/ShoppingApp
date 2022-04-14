package com.suveybesena.shoppingapp.presentation.productfeed

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suveybesena.shoppingapp.common.Resource
import com.suveybesena.shoppingapp.data.remote.model.MakeupItemsResponse
import com.suveybesena.shoppingapp.domain.usecase.GetAllProductItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductFeedViewModel @Inject constructor(var getAllProductItemsUseCase: GetAllProductItemsUseCase) :ViewModel() {

    private val products: MutableLiveData<MakeupItemsResponse> = MutableLiveData()
    val _products = products
    private val loadingState = MutableLiveData<Boolean?>()
    val _loadingState = loadingState
    private val errorState = MutableLiveData<String?>()
    val _errorState = errorState

    init {
        getProducts("maybelline")
    }

  fun getProducts (brandName: String){
      viewModelScope.launch {
          getAllProductItemsUseCase.invoke(brandName).collect { resource->
              when(resource){
                  is Resource.Success -> {
                      products.value = resource.data as MakeupItemsResponse
                      loadingState.value = false
                      errorState.value = false.toString()
                  }
                  is Resource.Error -> {
                      products.value = null
                      loadingState.value = false

                  }

                  is Resource.Loading -> {
                      products.value = null
                      loadingState.value = true
                      errorState.value = null
                  }
              }

          }
      }

  }

}