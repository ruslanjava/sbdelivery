package ru.skillbranch.sbdelivery.ui.dishList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.skillbranch.sbdelivery.R
import ru.skillbranch.sbdelivery.orm.entities.dishes.Dish

class DishListAdapter(
    private val addListener: (Dish) -> Unit,
    private val favoriteListener: (Dish) -> Unit,
    private val clickListener: (Dish) -> Unit
): RecyclerView.Adapter<DishViewHolder>() {

    var items: List<Dish> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.view_list_item_dish, parent, false)
        return DishViewHolder(view, addListener, favoriteListener, clickListener)
    }

    override fun onBindViewHolder(holder: DishViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateItems(dishes: List<Dish>) {
        val callback = DishDiffUtilCallback(items, dishes)
        val result = DiffUtil.calculateDiff(callback)
        items = dishes
        result.dispatchUpdatesTo(this)
    }

    private class DishDiffUtilCallback(
        private val oldItems: List<Dish>,
        private val newItems: List<Dish>
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
                    oldItem.favorite == newItem.favorite &&
                    oldItem.updatedAt == newItem.updatedAt
        }

    }

}