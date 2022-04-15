package com.suveybesena.shoppingapp.di

import android.content.Context
import androidx.room.Room
import com.suveybesena.shoppingapp.common.Constants
import com.suveybesena.shoppingapp.data.local.ProductDAO
import com.suveybesena.shoppingapp.data.local.ProductsDatabase
import com.suveybesena.shoppingapp.data.remote.MakeupItemsAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.API_URL)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideMyAPI(retrofit: Retrofit): MakeupItemsAPI {
        return retrofit.create(MakeupItemsAPI::class.java)
    }


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