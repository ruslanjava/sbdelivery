package ru.skillbranch.sbdelivery.ui.screens.cart

import android.view.View
import android.widget.NumberPicker
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.skillbranch.sbdelivery.R
import ru.skillbranch.sbdelivery.databinding.ViewListItemCartBinding
import ru.skillbranch.sbdelivery.orm.entities.cart.CartItemFull
import ru.skillbranch.sbdelivery.orm.entities.dishes.Dish
import ru.skillbranch.sbdelivery.ui.custom.NumberPickerView

class CartItemViewHolder(
    itemView: View,
    amountListener: (Dish, Int) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    private val ivCartItemIcon: AppCompatImageView
    private val tvNameView: AppCompatTextView
    private val tvNumberView: NumberPickerView
    private val tvFullPriceView: AppCompatTextView

    private val itemValueListener: CertItemValueListener = CertItemValueListener(amountListener)

    init {
        val binding = ViewListItemCartBinding.bind(itemView)
        ivCartItemIcon = binding.cartItemIcon
        tvNameView = binding.cartItemPrice

        tvNumberView = binding.cartItemNumber
        tvNumberView.setOnValueChangedListener(itemValueListener)

        tvFullPriceView = binding.cartItemPrice
    }

    fun bind(item: CartItemFull) {
        Glide.with(itemView.context)
            .load(item.dish.image)
            .placeholder(R.drawable.ic_more_28)
            .error(R.drawable.ic_error_28)
            .into(ivCartItemIcon)

        tvNameView.text = item.dish.name
        tvNumberView.setValue(item.amount)
        tvFullPriceView.text = item.fullPrice.toString()

        itemValueListener.dish = item.dish
    }

    internal class CertItemValueListener(
        private val amountListener: (Dish, Int) -> Unit
    ): NumberPicker.OnValueChangeListener {

        var dish: Dish? = null

        override fun onValueChange(picker: NumberPicker?, oldVal: Int, newVal: Int) {
            dish?.let { amountListener.invoke(it, newVal) }
        }

    }

}