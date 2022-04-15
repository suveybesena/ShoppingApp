package com.suveybesena.shoppingapp.domain.usecase

import com.suveybesena.shoppingapp.common.Resource
import com.suveybesena.shoppingapp.data.remote.model.MakeupItemResponseItem
import com.suveybesena.shoppingapp.domain.repository.ShoppingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class InsertProductsUseCase @Inject constructor(var repository: ShoppingRepository) {

    suspend fun invoke (products : MakeupItemResponseItem)= flow {
        emit(Resource.Loading)
        try {
            val upsertProducts =repository.insert(products)
            emit(Resource.Success(upsertProducts))
        }catch (e:Exception){
            println(e.localizedMessage)
            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(Dispatchers.IO)


}