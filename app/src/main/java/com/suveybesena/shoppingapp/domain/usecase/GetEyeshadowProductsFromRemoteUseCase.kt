package com.suveybesena.shoppingapp.domain.usecase

import com.suveybesena.shoppingapp.common.Resource
import com.suveybesena.shoppingapp.data.repository.ShoppingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetEyeshadowProductsFromRemoteUseCase @Inject constructor(val repository: ShoppingRepository) {

    suspend fun invoke() = flow {
        emit(Resource.Loading)
        try {
            val eyeshadowProducts = repository.getEyeshadowProducts()
            emit(Resource.Success(eyeshadowProducts))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(Dispatchers.IO)

}