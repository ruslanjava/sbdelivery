package ru.skillbranch.sbdelivery.ui.screens.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import ru.skillbranch.sbdelivery.databinding.FragmentCartBinding

class CartFragment : Fragment() {

    private val viewModel: CartItemViewModel by viewModels()

    private lateinit var rvCartList: RecyclerView
    private lateinit var adapter: CartListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCartBinding.inflate(inflater)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.cart().observe(viewLifecycleOwner, Observer { cart ->
            adapter.updateItems(cart.items)

        })
    }

}