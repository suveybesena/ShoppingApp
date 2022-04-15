package com.suveybesena.shoppingapp.domain.usecase

import com.suveybesena.shoppingapp.common.Resource
import com.suveybesena.shoppingapp.domain.repository.ShoppingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception
import javax.inject.Inject

class GetProductsFromLocalUseCase @Inject constructor(val repository: ShoppingRepository) {

    suspend fun invoke() = flow {

        emit(Resource.Loading)
        try {
            val getLocalProducts = repository.getAllProducts()
            emit(Resource.Success(getLocalProducts))

        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))

        }

    }.flowOn(Dispatchers.IO)


}