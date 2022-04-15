package com.suveybesena.shoppingapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.suveybesena.shoppingapp.data.remote.model.MakeupItemResponseItem



@Database (
    entities = [MakeupItemResponseItem::class],
    version = 2,
    exportSchema = false
        )

abstract class ProductsDatabase :RoomDatabase() {
    abstract fun productDao() :ProductDAO
}