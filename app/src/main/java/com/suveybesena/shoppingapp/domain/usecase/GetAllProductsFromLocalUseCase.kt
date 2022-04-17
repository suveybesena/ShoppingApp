package com.suveybesena.shoppingapp.domain.usecase

import com.suveybesena.shoppingapp.common.Resource
import com.suveybesena.shoppingapp.data.repository.ShoppingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAllProductsFromLocalUseCase @Inject constructor(val repository: ShoppingRepository) {

    suspend fun invoke() = flow {
        emit(Resource.Loading)
        try {
            val getAllProducts = repository.getAllProductsFromLocal()
            emit(Resource.Success(getAllProducts))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
            println(Resource.Error(e.localizedMessage))
        }
    }.flowOn(Dispatchers.IO)
}