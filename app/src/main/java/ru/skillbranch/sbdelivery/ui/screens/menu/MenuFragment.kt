package ru.skillbranch.sbdelivery.ui.screens.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import ru.skillbranch.sbdelivery.databinding.FragmentMenuBinding
import ru.skillbranch.sbdelivery.ui.screens.RootActivity

class MenuFragment : Fragment() {

    private val viewModel: MenuViewModel by viewModels()

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

        val customToolbar = binding.toolbar
        val activity = activity as RootActivity
        activity.setSupportActionBar(customToolbar)

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.categories().observe(viewLifecycleOwner, Observer { categories ->
            adapter.updateItems(categories)
        })

        viewModel.categoryClicks().observe(viewLifecycleOwner, Observer { category ->

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