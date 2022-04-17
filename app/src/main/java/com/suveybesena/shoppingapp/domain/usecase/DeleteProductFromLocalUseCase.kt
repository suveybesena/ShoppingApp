package com.suveybesena.shoppingapp.domain.usecase

import com.suveybesena.shoppingapp.common.Resource
import com.suveybesena.shoppingapp.domain.repository.ShoppingRepository
import com.suveybesena.shoppingapp.presentation.productfeed.main.ProductFeatures
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteProductFromLocalUseCase @Inject constructor(val repository: ShoppingRepository) {

    suspend fun invoke(product: ProductFeatures) = flow {
        emit(Resource.Loading)
        try {
            val deleteProduct = repository.delete(product)
            emit(Resource.Success(deleteProduct))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }
}