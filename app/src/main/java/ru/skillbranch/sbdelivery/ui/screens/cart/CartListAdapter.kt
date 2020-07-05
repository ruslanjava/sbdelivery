package ru.skillbranch.sbdelivery.ui.screens.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.skillbranch.sbdelivery.R
import ru.skillbranch.sbdelivery.http.data.review.ReviewRes
import ru.skillbranch.sbdelivery.orm.entities.cart.CartItem

class CartListAdapter(): RecyclerView.Adapter<CartItemViewHolder>() {

    var items: List<CartItem> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.view_list_item_cart, parent, false)
        return CartItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateItems(items: List<CartItem>) {
        val callback = CartItemDiffUtilCallback(items, items)
        val result = DiffUtil.calculateDiff(callback)
        this.items = items
        result.dispatchUpdatesTo(this)
    }

    internal class CartItemDiffUtilCallback(
        private val oldItems: List<ReviewRes>, private val newItems: List<ReviewRes>
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
                    oldItem.author.equals(newItem.author) &&
                    oldItem.text.equals(newItem.text) &&
                    oldItem.updatedAt == newItem.updatedAt
        }

    }

}