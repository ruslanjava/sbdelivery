package ru.skillbranch.sbdelivery.orm

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.skillbranch.sbdelivery.orm.entities.dishes.Dish

// блюда
@Dao
abstract class DishDao {

    @Transaction
    open fun upsert(dishes: List<Dish>) {
        insert(dishes)
            .mapIndexed { index, insertResult -> if (insertResult == -1L) dishes[index] else null }
            .filterNotNull()
            .also { if (it.isNotEmpty()) update(it) }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(dishes: List<Dish>): List<Long>

    @Query("SELECT * FROM dish")
    abstract fun getDishes(): List<Dish>

    @Query("SELECT * FROM dish WHERE oldPrice > 0")
    abstract fun getSaleDishes(): LiveData<List<Dish>>

    @Query("SELECT * FROM dish WHERE oldPrice > 0 LIMIT 1")
    abstract fun getFirstSaleDish(): Dish?

    @Query("SELECT * FROM dish WHERE recommended = 1")
    abstract fun getRecommendedDishes(): List<Dish>

    @Query("SELECT * FROM dish WHERE rating >= 4.8 LIMIT 10 ")
    abstract fun getBestDishes(): LiveData<List<Dish>>

    @Query("SELECT * FROM dish ORDER BY likes DESC LIMIT 10 ")
    abstract fun getPopularDishes(): LiveData<List<Dish>>

    @Query("SELECT * FROM dish LIMIT 1")
    abstract fun getFirstDish(): Dish?

    @Query("SELECT * FROM dish WHERE id = :id LIMIT 1")
    abstract fun findDish(id: String): Dish?

    @Query("SELECT * FROM dish WHERE favorite = 1")
    abstract fun getFavoriteDishes(): List<Dish>

    @Query("SELECT * FROM dish WHERE category_id = :categoryId")
    abstract fun getDishes(categoryId: String): List<Dish>

    @Insert
    abstract fun insert(dish: Dish)

    @Update
    abstract fun update(dish: Dish)

    @Update
    abstract fun update(dishes: List<Dish>)

    @Transaction
    open fun updateRecommendedDishes(ids: List<String>) {
        resetRecommended()
        setRecommended(ids)
    }

    @Transaction
    open fun clearTables() {
        deleteDishes()
    }

    @Query("DELETE FROM dish")
    protected abstract fun deleteDishes()

    @Query("UPDATE dish SET recommended = 0")
    protected abstract fun resetRecommended()

    @Query("UPDATE dish SET recommended = 1 WHERE id IN (:ids)")
    protected abstract fun setRecommended(ids: List<String>)

    open fun hasSaleDishes(): Boolean {
        return getFirstSaleDish() != null
    }

}