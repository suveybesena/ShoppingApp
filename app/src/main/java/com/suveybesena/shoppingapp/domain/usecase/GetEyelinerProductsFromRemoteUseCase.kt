package com.suveybesena.shoppingapp.domain.usecase

import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.suveybesena.shoppingapp.common.Resource
import com.suveybesena.shoppingapp.data.local.ProductDAO
import com.suveybesena.shoppingapp.data.local.ProductDatabase
import com.suveybesena.shoppingapp.data.repository.ShoppingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetEyelinerProductsFromRemoteUseCase @Inject constructor(var repository: ShoppingRepository) {

    suspend fun invoke() = flow {
        emit(Resource.Loading)
        try {
            val eyelinerProducts = repository.getEyelinerProducts()
            emit(Resource.Success(eyelinerProducts))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }

    }.flowOn(Dispatchers.IO)
}