package ru.skillbranch.sbdelivery.prefs

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class PrefProfileInstrumentedTest {

    @Test
    fun testPrefProfile() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val prefLogin = PrefProfile(appContext)
        prefLogin.email = "panarin@gmail.com"
        prefLogin.firstName = "Igor"
        prefLogin.lastName = "Panarin"

        val reloaded = PrefProfile(appContext)
        assertThat(reloaded.email, CoreMatchers.`is`("id2020@gmail.com"))
        assertThat(reloaded.firstName, CoreMatchers.`is`("Igor"))
        assertThat(reloaded.lastName, CoreMatchers.`is`("Panarin"))
    }

}