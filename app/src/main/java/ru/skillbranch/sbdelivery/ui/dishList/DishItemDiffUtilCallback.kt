package ru.skillbranch.sbdelivery.ui.dishList

import androidx.recyclerview.widget.DiffUtil
import ru.skillbranch.sbdelivery.orm.entities.dishes.Dish

internal class DishItemDiffUtilCallback(
    private val oldItems: List<Dish>, private val newItems: List<Dish>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldItems.size
    }

    override fun getNewListSize(): Int {
        return newItems.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItems[oldItemPosition]
        val newItem = newItems[newItemPosition]
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItems[oldItemPosition]
        val newItem = newItems[newItemPosition]
        return oldItem.id == newItem.id &&
                oldItem.name.equals(newItem.name) &&
                oldItem.updatedAt == newItem.updatedAt
    }

}