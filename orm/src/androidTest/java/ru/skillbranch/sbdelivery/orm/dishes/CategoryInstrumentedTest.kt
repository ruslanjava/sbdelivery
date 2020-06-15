package ru.skillbranch.sbdelivery.orm.dishes

import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.hamcrest.CoreMatchers
import org.junit.Test
import org.junit.runner.RunWith
import ru.skillbranch.sbdelivery.orm.DeliveryDatabase
import ru.skillbranch.sbdelivery.orm.entities.dishes.Category

@RunWith(AndroidJUnit4::class)
class CategoryInstrumentedTest {

    @Test
    fun testGetCategories() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val dishDao = DeliveryDatabase.getInstance(
            appContext
        ).dishDao()
        dishDao.clearTables()

        val categoryMain = newCategory("1", "Sushi and rolly", 1, "http://images.ru/img1.jpg")
        dishDao.insert(categoryMain)

        val categoryMainA = newCategory("2", "Sushi", 1, "http://images.ru/img2.jpg", "1")
        dishDao.insert(categoryMainA)
        val categoryMainB = newCategory("3", "Rolly", 1, "http://images.ru/img3.jpg", "1")
        dishDao.insert(categoryMainB)
        val categoryMainC = newCategory("4", "Miso soup", 1, "http://images.ru/img4.jpg", "1")
        dishDao.insert(categoryMainC)

        val rootCategories = dishDao.getRootCategories()
        assertThat(rootCategories.size, CoreMatchers.`is`(1))

        val children = dishDao.getChildCategories(rootCategories[0].id)
        assertThat(children.size, CoreMatchers.`is`(3))
    }

    private fun newCategory(id: String, name: String, order: Int, image: String, parentId: String? = null): Category {
        val category = Category()
        category.id = id
        category.name = name
        category.order = order
        category.image = image
        if (parentId != null) {
            category.parentId = parentId
        }
        return category
    }

}