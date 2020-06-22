package ru.skillbranch.sbdelivery.http

import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.*
import org.hamcrest.CoreMatchers

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import retrofit2.HttpException
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query
import ru.skillbranch.sbdelivery.http.auth.NotAuthorizedException
import ru.skillbranch.sbdelivery.http.data.review.ReviewRes

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
            val service = SbDeliveryServiceFactory.instance
            service.categories(offset = 0, limit = 10)
        }
        assertThat(categories.size, CoreMatchers.`is`(10))
    }

    @Test
    fun testDishes() {
        val dishes = runBlocking {
            val service = SbDeliveryServiceFactory.instance
            service.dishes(offset = 0, limit = 10)
        }
        assertThat(dishes.size, CoreMatchers.`is`(10))
    }

    @Test
    fun testFavoriteDishes() {
        try {
            runBlocking {
                val service = SbDeliveryServiceFactory.instance
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
                val service = SbDeliveryServiceFactory.instance
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
            val service = SbDeliveryServiceFactory.instance
            service.reviews("5ed8da011f071c00465b2026", 0, 10)
        }
        assertThat(reviews.size, CoreMatchers.`is`(0))
    }

}