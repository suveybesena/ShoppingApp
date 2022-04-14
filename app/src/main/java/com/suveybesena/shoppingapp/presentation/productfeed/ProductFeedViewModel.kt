package com.suveybesena.shoppingapp.presentation.productfeed

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suveybesena.shoppingapp.common.Resources
import com.suveybesena.shoppingapp.data.remote.MakeupItemsResponse
import com.suveybesena.shoppingapp.domain.repository.ShoppingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ProductFeedViewModel @Inject constructor(var repository: ShoppingRepository) :ViewModel() {

    val products: MutableLiveData<Resources<MakeupItemsResponse>> = MutableLiveData()

    init {
        products("maybelline")
    }

    fun products(brandName: String) = viewModelScope.launch {
        products.postValue(Resources.loading())
        val response = repository.getProducts(brandName)
        products.postValue(handleNewsResponse(response))
    }

    private fun handleNewsResponse(response: Response<MakeupItemsResponse>): Resources<MakeupItemsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return resultResponse.let {
                    Resources.success(it)
                }
            }
        }
        return Resources.error(response.message())
    }

}