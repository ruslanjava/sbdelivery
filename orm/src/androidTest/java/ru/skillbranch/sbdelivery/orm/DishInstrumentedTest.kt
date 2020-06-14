package ru.skillbranch.sbdelivery.orm

import androidx.test.platform.app.InstrumentationRegistry
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Test
import ru.skillbranch.sbdelivery.orm.entities.dishes.Dish

class DishInstrumentedTest {

    @Test
    fun testDishDao() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val dishDao = DeliveryDatabase.getInstance(appContext).dishDao()
        dishDao.clearTables()

        val dish = newDish("1", "Lulya-Kebab", "Lyluya-Kebab with soar milk")
        dishDao.insert(dish)

        val reloaded = dishDao.getDishes()
        Assert.assertThat(reloaded.size, CoreMatchers.`is`(1))

        val newDish = reloaded[0]
        Assert.assertThat(reloaded, CoreMatchers.notNullValue())

        Assert.assertThat(newDish.id, CoreMatchers.`is`("1"))
        Assert.assertThat(newDish.name, CoreMatchers.`is`("Lulya-Kebab"))
        Assert.assertThat(newDish.description, CoreMatchers.`is`("Lyluya-Kebab with soar milk"))
    }

    private fun newDish(id: String, name: String, description: String): Dish {
        val dish = Dish()
        dish.id = id
        dish.name = name
        dish.description = description
        return dish
    }

}