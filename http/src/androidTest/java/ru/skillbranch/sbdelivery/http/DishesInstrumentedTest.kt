package ru.skillbranch.sbdelivery.http

import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Test
import kotlin.system.measureTimeMillis

class DishesInstrumentedTest {

    @Test
    fun testGetAllDishes() {
        val duration = measureTimeMillis {
            val dishes = runBlocking {
                HttpClient.getAllDishes()
            }
            Assert.assertThat(dishes.size, CoreMatchers.`is`(306))
        }
        println("Get all dishes time: $duration")
    }

}