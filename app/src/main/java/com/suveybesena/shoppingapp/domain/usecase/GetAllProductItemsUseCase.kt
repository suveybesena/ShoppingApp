package com.suveybesena.shoppingapp.domain.usecase


import com.suveybesena.shoppingapp.common.Resource
import com.suveybesena.shoppingapp.domain.repository.ShoppingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAllProductItemsUseCase @Inject constructor(val repository: ShoppingRepository) {

    suspend fun invoke(brandName : String) = flow {
        emit(Resource.Loading)
        try {
            val products= repository.getProducts(brandName)
            emit(Resource.Success(products))
        }catch (e:Exception){
            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(Dispatchers.IO)
}