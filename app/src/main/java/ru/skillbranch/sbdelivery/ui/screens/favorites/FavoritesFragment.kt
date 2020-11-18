package ru.skillbranch.sbdelivery.ui.screens.favorites

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
import ru.skillbranch.sbdelivery.databinding.FragmentFavoritesBinding
import ru.skillbranch.sbdelivery.ui.dishList.DishListAdapter
import ru.skillbranch.sbdelivery.ui.screens.RootActivity
import ru.skillbranch.sbdelivery.ui.screens.dish.DishFragmentArgs

class FavoritesFragment : DaggerFragment() {

    private lateinit var rvFavoritesList: RecyclerView
    private lateinit var adapter: DishListAdapter

    private val viewModel: FavoritesViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        androidInjector().inject(viewModel)
    }

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
        viewModel.observeFavoriteDishes(viewLifecycleOwner) { dishes ->
            adapter.updateItems(dishes)
        }

        viewModel.observeClickedDishes(viewLifecycleOwner) { dish ->
            dish?.let {
                val activity = activity as RootActivity
                val navController = activity.navController
                navController.navigate(R.id.action_nav_favorites_to_nav_dish, DishFragmentArgs(it.id).toBundle())
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