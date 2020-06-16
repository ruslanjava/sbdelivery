package ru.skillbranch.sbdelivery.prefs

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

@RunWith(AndroidJUnit4::class)
class PrefLoginInstrumentedTest {

    @Test
    fun testPrefLogin() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val prefLogin = PrefLogin(appContext)
        prefLogin.clear()

        prefLogin.email = "skillbranch@gmail.com"
        prefLogin.password = "test a b c"

        val reloaded = PrefLogin(appContext)
        assertThat(reloaded.email, CoreMatchers.`is`("skillbranch@gmail.com"))
        assertThat(reloaded.password, CoreMatchers.`is`("test a b c"))
    }

}