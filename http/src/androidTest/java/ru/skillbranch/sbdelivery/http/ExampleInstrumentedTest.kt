package ru.skillbranch.sbdelivery.http

import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.*
import org.hamcrest.CoreMatchers

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Test
    fun testCategories() {
        val categories = runBlocking {
            val service = SbDeliveryClient.instance
            service.categories(offset = 0, limit = 10)
        }
        assertThat(categories.size, CoreMatchers.`is`(10))
    }

    @Test
    fun testDishes() {
        val dishes = runBlocking {
            val service = SbDeliveryClient.instance
            service.dishes(offset = 0, limit = 10)
        }
        assertThat(dishes.size, CoreMatchers.`is`(10))
    }

}