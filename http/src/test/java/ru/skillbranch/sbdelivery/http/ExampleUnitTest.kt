package ru.skillbranch.sbdelivery.http

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.hamcrest.CoreMatchers.`is`
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    private val scope = CoroutineScope(Dispatchers.IO)

    @Test
    fun testCategories() {
        scope.launch {
            val service = NetworkManager.api
            val categories = service.categories(1, 10)
            assertThat(categories.size, `is`(10))
        }
    }

}