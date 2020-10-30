package ru.skillbranch.sbdelivery.http

import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.*
import org.hamcrest.CoreMatchers

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import retrofit2.HttpException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class SbDeliveryServiceInstrumentedTest {

    @Test
    fun testCategories() {
        val categories = runBlocking {
            val service = NetworkManager.api
            service.categories(offset = 0, limit = 10)
        }
        assertThat(categories.size, CoreMatchers.`is`(10))
    }

    @Test
    fun testDishes() {
        val dishes = runBlocking {
            val service = NetworkManager.api
            service.dishes(offset = 0, limit = 10)
        }
        assertThat(dishes.size, CoreMatchers.`is`(10))
    }

    @Test
    fun testFavoriteDishes() {
        try {
            runBlocking {
                val service = NetworkManager.api
                service.favoriteDishes(offset = 0, limit = 10)
            }
            fail("Cannot be here")
        } catch (e: HttpException) {
            assertThat(e.code(), CoreMatchers.`is`(402))
        }
    }

    @Test
    fun testProfile() {
        try {
            runBlocking {
                val service = NetworkManager.api
                service.profile()
            }
            fail("Cannot be here")
        } catch (e: HttpException) {
            assertThat(e.code(), CoreMatchers.`is`(402))
        }
    }

    @Test
    fun testReviews() {
        val reviews = runBlocking {
            val service = NetworkManager.api
            service.reviews("5ed8da011f071c00465b2026", 0, 10)
        }
        assertThat(reviews.size, CoreMatchers.`is`(0))
    }

}