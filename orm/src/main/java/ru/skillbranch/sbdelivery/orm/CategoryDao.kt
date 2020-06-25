package ru.skillbranch.sbdelivery.orm

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import ru.skillbranch.sbdelivery.orm.entities.dishes.Category

// категория
@Dao
abstract class CategoryDao {

    @Transaction
    open fun upsert(categories: List<Category>) {
        insert(categories)
            .mapIndexed { index, insertResult -> if (insertResult == -1L) categories[index] else null }
            .filterNotNull()
            .also { if (it.isNotEmpty()) update(it) }
    }

    @Query("SELECT * FROM category WHERE parent_id = 'root'")
    abstract fun getRootCategories(): List<Category>

    @Query("SELECT * FROM category WHERE parent_id = :parentId")
    abstract fun getChildCategories(parentId: String): List<Category>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(categories: List<Category>): List<Long>

    @Insert
    abstract fun update(category: Category)

    @Insert
    abstract fun update(categories: List<Category>)

    @Query("DELETE FROM category")
    protected abstract fun deleteCategories()

    @Transaction
    open fun clearTables() {
        deleteCategories()
    }

}