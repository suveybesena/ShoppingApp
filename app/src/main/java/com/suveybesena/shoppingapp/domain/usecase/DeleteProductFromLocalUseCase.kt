package com.suveybesena.shoppingapp.domain.usecase

import com.suveybesena.shoppingapp.common.Resource
import com.suveybesena.shoppingapp.data.model.ProductFeatures
import com.suveybesena.shoppingapp.data.repository.ShoppingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class DeleteProductFromLocalUseCase @Inject constructor(val repository: ShoppingRepository) {

    suspend fun invoke(product: ProductFeatures) = flow {
        emit(Resource.Loading)
        try {
            val deleteProduct = repository.deleteProduct(product)
            emit(Resource.Success(deleteProduct))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(Dispatchers.IO)
}