package ru.skillbranch.sbdelivery.ui.screens.dish

import android.content.Context
import android.view.View
import android.widget.RatingBar
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import ru.skillbranch.sbdelivery.databinding.ViewListItemCommentBinding
import ru.skillbranch.sbdelivery.http.data.review.ReviewRes

class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val context: Context = itemView.context

    private val tvNameView: AppCompatTextView
    private val tvTextView: AppCompatTextView
    private val ratingBar: RatingBar

    init {
        val binding = ViewListItemCommentBinding.bind(itemView)

        tvNameView = binding.commentName
        ratingBar = binding.commentRating
        tvTextView = binding.commentText
    }

    fun bind(item: ReviewRes) {
        tvNameView.text = item.author
        tvTextView.text = item.text
        ratingBar.rating = item.rating
    }

}