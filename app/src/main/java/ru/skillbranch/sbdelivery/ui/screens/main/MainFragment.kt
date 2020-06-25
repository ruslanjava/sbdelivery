package ru.skillbranch.sbdelivery.ui.screens.main

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.constraintlayout.widget.Group
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.skillbranch.sbdelivery.R
import ru.skillbranch.sbdelivery.ui.screens.RootActivity
import ru.skillbranch.sbdelivery.databinding.FragmentMainBinding
import ru.skillbranch.sbdelivery.orm.entities.dishes.Dish
import ru.skillbranch.sbdelivery.ui.dishList.DishAdapter
import ru.skillbranch.sbdelivery.ui.dishList.DishItemDiffUtilCallback

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var drawerToggle: ActionBarDrawerToggle

    private lateinit var recommendedGroup: Group
    private lateinit var rvRecommendedList: RecyclerView
    private lateinit var recommendedAdapter: DishAdapter

    private lateinit var bestGroup: Group
    private lateinit var rvBestList: RecyclerView
    private lateinit var bestAdapter: DishAdapter

    private lateinit var popularGroup: Group
    private lateinit var rvPopularList: RecyclerView
    private lateinit var popularAdapter: DishAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater)
        val view = binding.root

        val customToolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)
        val activity = activity as RootActivity
        activity.setSupportActionBar(customToolbar)

        recommendedGroup = view.findViewById(R.id.recommended_group)
        rvRecommendedList = view.findViewById(R.id.rv_recommended_list)
        recommendedAdapter = createDishAdapter()
        rvRecommendedList.adapter = recommendedAdapter

        bestGroup = view.findViewById(R.id.best_group)
        rvBestList = view.findViewById(R.id.rv_best_list)
        bestAdapter = createDishAdapter()
        rvBestList.adapter = bestAdapter

        popularGroup = view.findViewById(R.id.popular_group)
        rvPopularList = view.findViewById(R.id.rv_popular_list)
        popularAdapter = createDishAdapter()
        rvPopularList.adapter = popularAdapter

        drawerLayout = view.findViewById(R.id.drawer_layout) as DrawerLayout
        drawerToggle = object : ActionBarDrawerToggle(
            activity,  /* host Activity */
            drawerLayout,  /* DrawerLayout object */
            customToolbar,  /* nav drawer icon to replace 'Up' caret */
            R.string.main_drawer_open,  /* "open drawer" description */
            R.string.main_drawer_close /* "close drawer" description */
        ) {

        }

        setHasOptionsMenu(true)
        return view
    }

    @ExperimentalCoroutinesApi
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        viewModel.recommendedDishes().observe(viewLifecycleOwner, Observer<List<Dish>> { dishes ->
            recommendedAdapter.updateItems(dishes)
            recommendedGroup.visibility = if (dishes.isEmpty()) View.GONE else View.VISIBLE
        })

        viewModel.bestDishes().observe(viewLifecycleOwner, Observer<List<Dish>> { dishes ->
            bestAdapter.updateItems(dishes)
            bestGroup.visibility = if (dishes.isEmpty()) View.GONE else View.VISIBLE
        })

        viewModel.popularDishes().observe(viewLifecycleOwner, Observer<List<Dish>> { dishes ->
            popularAdapter.updateItems(dishes)
            popularGroup.visibility = if (dishes.isEmpty()) View.GONE else View.VISIBLE
        })

        drawerToggle.syncState()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
                drawerLayout.closeDrawer(Gravity.LEFT)
                return true
            } else {
                findNavController().navigateUp()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun createDishAdapter(): DishAdapter {
        return DishAdapter(
            { dish -> viewModel.handleAddClick(dish) },
            { dish -> viewModel.handleDishClick(dish) }
        )
    }

    private fun DishAdapter.updateItems(dishes: List<Dish>) {
        val callback = DishItemDiffUtilCallback(items, dishes)
        val result = DiffUtil.calculateDiff(callback)
        items = dishes
        result.dispatchUpdatesTo(this)
    }

}
