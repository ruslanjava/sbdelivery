package ru.skillbranch.sbdelivery.orm.dishes

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import ru.skillbranch.sbdelivery.orm.DeliveryDatabase
import ru.skillbranch.sbdelivery.orm.entities.dishes.Dish

@RunWith(AndroidJUnit4::class)
class DishInstrumentedTest {

    @Test
    fun testGetDishes() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val dishDao = DeliveryDatabase.getInstance(
            appContext
        ).dishDao()
        dishDao.clearTables()

        val dish: Dish = newDish(
            id = "1",
            name = "Lulya-Kebab",
            description = "Lyluya-Kebab with soar milk",
            image = "http://images.ru/image1.jpg",
            oldPrice = 120,
            price = 100
        )
        dishDao.insert(dish)

        val reloadedDishes = dishDao.getDishes()
        Assert.assertThat(reloadedDishes.size, CoreMatchers.`is`(1))

        val reloaded = reloadedDishes[0]
        Assert.assertThat(reloaded, CoreMatchers.notNullValue())

        Assert.assertThat(reloaded.id, CoreMatchers.`is`(dish.id))
        Assert.assertThat(reloaded.name, CoreMatchers.`is`(dish.name))
        Assert.assertThat(reloaded.description, CoreMatchers.`is`(dish.description))
        Assert.assertThat(reloaded.image, CoreMatchers.`is`(dish.image))
        Assert.assertThat(reloaded.oldPrice, CoreMatchers.`is`(dish.oldPrice))
        Assert.assertThat(reloaded.price, CoreMatchers.`is`(dish.price))
    }

    private fun newDish(id: String, name: String, description: String, image: String, oldPrice: Int?,
                        price: Int): Dish {
        val dish = Dish()
        dish.id = id
        dish.name = name
        dish.description = description
        dish.image = image
        dish.oldPrice = oldPrice
        dish.price = price
        return dish
    }

}