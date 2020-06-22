package ru.skillbranch.sbdelivery.http

import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Test
import kotlin.system.measureTimeMillis

class CategoriesInstrumentedTest {

    @Test
    fun testGetAllCategories() {
        val duration = measureTimeMillis {
            val categories = runBlocking {
                HttpClient.getAllCategories()
            }
            Assert.assertThat(categories.size, CoreMatchers.`is`(37))
        }
        println("Get all categories time: $duration")
    }

}