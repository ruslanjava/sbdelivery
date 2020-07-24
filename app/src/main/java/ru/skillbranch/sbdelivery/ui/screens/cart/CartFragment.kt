package ru.skillbranch.sbdelivery.ui.screens.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import ru.skillbranch.sbdelivery.databinding.FragmentCartBinding
import ru.skillbranch.sbdelivery.ui.screens.RootActivity

class CartFragment : Fragment() {

    private val viewModel: CartItemViewModel by viewModels()

    private lateinit var cartList: RecyclerView
    private lateinit var adapter: CartListAdapter

    private lateinit var promoField: EditText
    private lateinit var promoButton: Button
    private lateinit var promoText: AppCompatTextView

    private lateinit var totalLabel: AppCompatTextView
    private lateinit var totalValue: AppCompatTextView
    private lateinit var orderButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCartBinding.inflate(inflater)

        cartList = binding.rvCartList
        adapter = CartListAdapter {
            dish, amount -> viewModel.handleDishAmountChange(dish, amount)
        }
        cartList.adapter = adapter

        promoField = binding.cartPromoField
        promoButton = binding.cartPromoButton
        promoText = binding.cartPromoDescription

        totalLabel = binding.cartTotal
        totalValue = binding.cartTotalValue
        orderButton = binding.cartOrderButton

        val customToolbar = binding.toolbar
        val activity = activity as RootActivity
        activity.setSupportActionBar(customToolbar)

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.cart().observe(viewLifecycleOwner, Observer { cart ->
            if (cart != null) {
                adapter.updateItems(cart.items)

                val total = cart.items
                    .map { item -> item.getFullPrice() }
                    .sum()

                promoField.visibility = View.VISIBLE
                promoButton.visibility = View.VISIBLE

                totalLabel.visibility = View.VISIBLE
                totalValue.visibility = View.VISIBLE
                totalValue.text = total.toString()

                orderButton.visibility = View.VISIBLE
            } else {
                adapter.updateItems(listOf())

                promoField.visibility = View.INVISIBLE
                promoButton.visibility = View.INVISIBLE

                totalLabel.visibility = View.INVISIBLE
                totalValue.visibility = View.INVISIBLE

                orderButton.visibility = View.INVISIBLE
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            findNavController().navigateUp()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}