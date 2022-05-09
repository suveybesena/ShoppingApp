package com.suveybesena.shoppingapp.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.suveybesena.shoppingapp.data.model.ProductFeatures
import com.suveybesena.shoppingapp.getOrAwaitValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class ProductDaoTest {

    @get:Rule
    var instantTaskExecutorRole = InstantTaskExecutorRule()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("testDatabase")
    lateinit var database: ProductDatabase


    private lateinit var dao: ProductDAO

    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.productDao()
    }


    @Test
    fun insertProduct() = runBlockingTest {
        val product = ProductFeatures(
            1,
            "apple",
            "www.test.com",
            "www.image",
            "fruit",
            "$80"
        )

        dao.insertProduct(product)
        val list = dao.getAllProducts().getOrAwaitValue()
        assertThat(list).contains(product)

    }


    @After
    fun teardown() {
        database.close()
    }


}