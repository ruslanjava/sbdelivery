package ru.skillbranch.sbdelivery.ui.screens.cart

import android.content.Context
import android.view.View
import android.widget.RatingBar
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.skillbranch.sbdelivery.R
import ru.skillbranch.sbdelivery.databinding.ViewListItemCartBinding
import ru.skillbranch.sbdelivery.databinding.ViewListItemCommentBinding
import ru.skillbranch.sbdelivery.http.data.review.ReviewRes
import ru.skillbranch.sbdelivery.orm.entities.cart.CartItem

class CartItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val context: Context = itemView.context

    private val ivCartItemIcon: AppCompatImageView
    private val tvNameView: AppCompatTextView
    private val tvTextView: AppCompatTextView
    private val ratingBar: RatingBar

    init {
        val binding = ViewListItemCartBinding.bind(itemView)

        Glide.with(itemView.context)
            .load(item.image)
            .placeholder(R.drawable.ic_more_28)
            .error(R.drawable.ic_error_28)
            .into(ivBackground)

        tvNameView = binding.commentName
        ratingBar = binding.commentRating
        tvTextView = binding.commentText
    }

    fun bind(item: CartItem) {
        tvNameView.text = item.author
        tvTextView.text = item.text
        ratingBar.rating = item.rating
    }

}