package ru.skillbranch.sbdelivery.ui.screens.dish

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.skillbranch.sbdelivery.R
import ru.skillbranch.sbdelivery.http.data.review.ReviewRes

class CommentListAdapter(): RecyclerView.Adapter<CommentViewHolder>() {

    var items: List<ReviewRes> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.view_list_item_comment, parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateItems(reviews: List<ReviewRes>) {
        val callback = CategoryDiffUtilCallback(items, reviews)
        val result = DiffUtil.calculateDiff(callback)
        items = reviews
        result.dispatchUpdatesTo(this)
    }

    internal class CategoryDiffUtilCallback(
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