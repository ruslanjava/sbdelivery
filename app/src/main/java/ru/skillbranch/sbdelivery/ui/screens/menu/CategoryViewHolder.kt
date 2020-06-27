package ru.skillbranch.sbdelivery.ui.screens.menu

import android.content.Context
import android.graphics.drawable.PictureDrawable
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import ru.skillbranch.sbdelivery.R
import ru.skillbranch.sbdelivery.databinding.ViewListItemCategoryBinding
import ru.skillbranch.sbdelivery.glide.SvgSoftwareLayerSetter
import ru.skillbranch.sbdelivery.orm.entities.dishes.Category

class CategoryViewHolder(
    itemView: View, clickListener: (Category) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    private val context: Context = itemView.context

    private val ivIconView: AppCompatImageView
    private val tvNameView: AppCompatTextView

    private val itemClickListener: ItemClickListener = ItemClickListener(clickListener)

    private val requestBuilder: RequestBuilder<PictureDrawable>

    init {
        val binding = ViewListItemCategoryBinding.bind(itemView)
        itemView.setOnClickListener(itemClickListener)

        ivIconView = binding.menuItemIcon
        tvNameView = binding.menuItemName

        requestBuilder = Glide.with(itemView.context)
            .`as`(PictureDrawable::class.java)
            .placeholder(R.drawable.ic_more_28)
            .error(R.drawable.ic_error_28)
            .listener(SvgSoftwareLayerSetter())
    }

    fun bind(item: Category) {
        if (item == Category.SALES) {
            Glide.with(context)
                .load(R.drawable.ic_sale_28)
                .into(ivIconView)
            tvNameView.text = context.getString(R.string.menu_sales)
        } else {
            requestBuilder
                .load(item.image)
                .into(ivIconView)
            tvNameView.text = item.name
        }

        itemClickListener.category = item
    }

    internal class ItemClickListener(private val clickListener: (Category) -> Unit): View.OnClickListener {

        var category: Category? = null

        override fun onClick(v: View) { category?.let { clickListener.invoke(it) } }

    }

}