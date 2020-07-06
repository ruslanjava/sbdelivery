package ru.skillbranch.sbdelivery.ui.screens.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.skillbranch.sbdelivery.R
import ru.skillbranch.sbdelivery.orm.entities.cart.CartItemFull
import ru.skillbranch.sbdelivery.orm.entities.dishes.Dish

class CartListAdapter(private val amountListener: (Dish, Int) -> Unit): RecyclerView.Adapter<CartItemViewHolder>() {

    var items: List<CartItemFull> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.view_list_item_cart, parent, false)
        return CartItemViewHolder(view, amountListener)
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateItems(items: List<CartItemFull>) {
        val callback = CartItemDiffUtilCallback(items, items)
        val result = DiffUtil.calculateDiff(callback)
        this.items = items
        result.dispatchUpdatesTo(this)
    }

    internal class CartItemDiffUtilCallback(
        private val oldItems: List<CartItemFull>, private val newItems: List<CartItemFull>
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
            return oldItem.dish.id == newItem.dish.id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldItems[oldItemPosition]
            val newItem = newItems[newItemPosition]
            return oldItem.dish.id == newItem.dish.id &&
                    oldItem.amount.equals(newItem.amount) &&
                    oldItem.dish.name.equals(newItem.dish.name) &&
                    oldItem.dish.price == newItem.dish.price
        }

    }

}