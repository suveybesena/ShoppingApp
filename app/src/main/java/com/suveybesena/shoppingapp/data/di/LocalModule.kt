package com.suveybesena.shoppingapp.data.di

import android.content.Context
import androidx.room.Room
import com.suveybesena.shoppingapp.data.local.ProductDAO
import com.suveybesena.shoppingapp.data.local.ProductsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ProductsDatabase =
        Room.databaseBuilder(context, ProductsDatabase::class.java, "products_db")
            .fallbackToDestructiveMigration()
            .build()


    @Provides
    @Singleton
    fun provideDao(productsDatabase: ProductsDatabase): ProductDAO {
        return productsDatabase.productDao()
    }
}