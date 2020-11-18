package ru.skillbranch.sbdelivery.ui.screens.category.subCategoryList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.skillbranch.sbdelivery.R
import ru.skillbranch.sbdelivery.orm.entities.dishes.Category

class SubCategoryListAdapter(
    private val clickListener: (Category) -> Unit
): RecyclerView.Adapter<SubCategoryViewHolder>() {

    var items: List<Category> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubCategoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.view_list_item_sub_category, parent, false)
        return SubCategoryViewHolder(view, clickListener)
    }

    override fun onBindViewHolder(holder: SubCategoryViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateItems(categories: List<Category>) {
        val callback = CategoryDiffUtilCallback(items, categories)
        val result = DiffUtil.calculateDiff(callback)
        items = categories
        result.dispatchUpdatesTo(this)
    }

    internal class CategoryDiffUtilCallback(
        private val oldItems: List<Category>, private val newItems: List<Category>
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

}