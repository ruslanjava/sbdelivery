package ru.skillbranch.sbdelivery.orm

import androidx.room.*
import ru.skillbranch.sbdelivery.orm.entities.dishes.Category
import ru.skillbranch.sbdelivery.orm.entities.dishes.Dish

@Dao
abstract class DishDao {

    // категория

    @Query("SELECT * FROM category WHERE parent_id = 'root'")
    abstract fun getRootCategories(): List<Category>

    @Query("SELECT * FROM category WHERE parent_id = :parentId")
    abstract fun getChildCategories(parentId: String): List<Category>

    @Insert
    abstract fun insert(category: Category)

    @Insert
    abstract fun update(category: Category)

    // блюда

    @Query("SELECT * FROM dish")
    abstract fun getDishes(): List<Dish>

    @Query("SELECT * FROM dish WHERE favorite = 1")
    abstract fun getFavoriteDishes(): List<Dish>

    @Query("SELECT * FROM dish WHERE category_id = :categoryId")
    abstract fun getDishes(categoryId: String): List<Dish>

    @Insert
    abstract fun insert(dish: Dish)

    @Update
    abstract fun update(dish: Dish)

    @Transaction
    open fun clearTables() {
        deleteDishes()
        deleteCategories()
    }

    @Query("DELETE FROM category")
    protected abstract fun deleteCategories()

    @Query("DELETE FROM dish")
    protected abstract fun deleteDishes()

}