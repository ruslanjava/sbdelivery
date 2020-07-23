package ru.skillbranch.sbdelivery.ui.screens.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import ru.skillbranch.sbdelivery.R
import ru.skillbranch.sbdelivery.databinding.FragmentFavoritesBinding
import ru.skillbranch.sbdelivery.ui.dishList.DishListAdapter
import ru.skillbranch.sbdelivery.ui.screens.RootActivity
import ru.skillbranch.sbdelivery.ui.screens.dish.DishFragmentArgs

class FavoritesFragment : Fragment() {

    private val viewModel: FavoritesViewModel by viewModels()

    private lateinit var rvFavoritesList: RecyclerView
    private lateinit var adapter: DishListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFavoritesBinding.inflate(inflater)

        rvFavoritesList = binding.rvFavoritesList
        adapter = DishListAdapter(
            R.layout.view_list_item_dish_grid,
            { dish -> viewModel.handleAddClick(dish) },
            { dish -> viewModel.handleFavoriteClick(dish) },
            { dish -> viewModel.handleDishClick(dish) }
        )
        rvFavoritesList.adapter = adapter

        val customToolbar = binding.toolbar
        val activity = activity as RootActivity
        activity.setSupportActionBar(customToolbar)

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.favoriteDishes().observe(viewLifecycleOwner, Observer { dishes ->
            adapter.updateItems(dishes)
        })

        viewModel.clickedDishes().observe(viewLifecycleOwner, Observer { dish ->
            dish?.let {
                val activity = activity as RootActivity
                val navController = activity.navController
                navController.navigate(R.id.action_nav_favorites_to_nav_dish, DishFragmentArgs(it.id).toBundle())
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