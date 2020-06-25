package ru.skillbranch.sbdelivery.ui.dishList

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.skillbranch.sbdelivery.databinding.ViewListItemDishBinding
import ru.skillbranch.sbdelivery.orm.entities.dishes.Dish

class DishViewHolder(
    itemView: View,
    addListener: (Dish) -> Unit,
    clickListener: (Dish) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    private val tvSaleView: AppCompatTextView
    private val tvNameView: AppCompatTextView
    private val tvPriceView: AppCompatTextView

    private val ivBackground: AppCompatImageView

    private val addButtonListener: AddClickListener = AddClickListener(addListener)
    private val itemClickListener: ItemClickListener = ItemClickListener(clickListener)

    init {
        val binding = ViewListItemDishBinding.bind(itemView)
        itemView.setOnClickListener(itemClickListener)

        tvSaleView = binding.dishSale
        tvNameView = binding.dishName
        tvPriceView = binding.dishPrice

        ivBackground = binding.dishBackground

        val addButton = binding.addToCart
        addButton.setOnClickListener(addButtonListener)
    }

    fun bind(item: Dish) {
        tvSaleView.visibility = if (item.oldPrice != null) View.VISIBLE else View.INVISIBLE
        tvNameView.text = item.name
        tvPriceView.text = "${item.price} ₽"

        Glide.with(itemView.context)
            .load(item.image)
            .into(ivBackground)

        addButtonListener.dish = item
        itemClickListener.dish = item
    }

    internal class ItemClickListener(private val clickListener: (Dish) -> Unit): View.OnClickListener {

        var dish: Dish? = null

        override fun onClick(v: View) { dish?.let { clickListener.invoke(it) } }

    }

    internal class AddClickListener(private val addListener: (Dish) -> Unit): View.OnClickListener {

        var dish: Dish? = null

        override fun onClick(v: View) { dish?.let { addListener.invoke(it) } }

    }

}