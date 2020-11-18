package ru.skillbranch.sbdelivery.ui.screens.cart

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import dagger.android.support.DaggerFragment
import ru.skillbranch.sbdelivery.databinding.FragmentCartBinding
import ru.skillbranch.sbdelivery.ui.screens.RootActivity
import timber.log.Timber

class CartFragment : DaggerFragment() {

    private lateinit var cartList: RecyclerView
    private lateinit var adapter: CartListAdapter

    private lateinit var promoField: EditText
    private lateinit var promoButton: Button
    private lateinit var promoText: AppCompatTextView

    private lateinit var totalLabel: AppCompatTextView
    private lateinit var totalValue: AppCompatTextView
    private lateinit var orderButton: Button

    private val viewModel: CartItemViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        androidInjector().inject(viewModel)
    }

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

        viewModel.observeErrors(viewLifecycleOwner) { error ->
            val activity = activity as RootActivity
            Timber.e(error)
            activity.showError(error.message ?: error.toString())
        }

        viewModel.observeCart(viewLifecycleOwner) { cart ->
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
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            findNavController().navigateUp()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}