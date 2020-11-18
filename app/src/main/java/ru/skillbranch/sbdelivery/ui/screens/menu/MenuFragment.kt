package ru.skillbranch.sbdelivery.ui.screens.menu

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import dagger.android.support.DaggerFragment
import ru.skillbranch.sbdelivery.R
import ru.skillbranch.sbdelivery.databinding.FragmentMenuBinding
import ru.skillbranch.sbdelivery.ui.screens.RootActivity
import ru.skillbranch.sbdelivery.ui.screens.category.CategoryFragmentArgs

class MenuFragment : DaggerFragment() {

    private val viewModel: MenuViewModel by viewModels()

    private lateinit var rvMenuList: RecyclerView
    private lateinit var adapter: CategoryListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        androidInjector().inject(viewModel)
    }

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
        viewModel.observeCategories(viewLifecycleOwner) { categories ->
            adapter.updateItems(categories)
        }

        viewModel.observeCategoryClicks(viewLifecycleOwner) { category ->
            val activity = activity as RootActivity
            val navController = activity.navController
            val args = CategoryFragmentArgs(category.id)
            navController.navigate(R.id.action_nav_menu_to_nav_category, args.toBundle())
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