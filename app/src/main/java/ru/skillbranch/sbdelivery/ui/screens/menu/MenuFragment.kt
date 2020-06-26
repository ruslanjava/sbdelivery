package ru.skillbranch.sbdelivery.ui.screens.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import ru.skillbranch.sbdelivery.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {

    private lateinit var viewModel: MenuViewModel

    private lateinit var rvMenuList: RecyclerView
    private lateinit var adapter: CategoryListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMenuBinding.inflate(inflater)

        rvMenuList = binding.rvMenuList
        adapter = CategoryListAdapter { category -> viewModel.handleCategoryClick(category) }
        rvMenuList.adapter = adapter

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MenuViewModel::class.java)
        viewModel.categories().observe(viewLifecycleOwner, Observer { categories ->
            adapter.updateItems(categories)
        })

        viewModel.categoryClicks().observe(viewLifecycleOwner, Observer { category ->

        })
    }

}