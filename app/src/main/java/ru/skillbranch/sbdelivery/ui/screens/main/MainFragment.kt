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
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.skillbranch.sbdelivery.R
import ru.skillbranch.sbdelivery.ui.screens.RootActivity
import ru.skillbranch.sbdelivery.databinding.FragmentMainBinding
import ru.skillbranch.sbdelivery.databinding.FragmentMainContentBinding
import ru.skillbranch.sbdelivery.databinding.NavMenuMainBinding
import ru.skillbranch.sbdelivery.orm.entities.dishes.Dish
import ru.skillbranch.sbdelivery.ui.dishList.DishListAdapter
import ru.skillbranch.sbdelivery.ui.screens.main.MainMenuItem.*

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var drawerToggle: ActionBarDrawerToggle

    private lateinit var navHeaderGroup: Group

    private lateinit var recommendedGroup: Group
    private lateinit var rvRecommendedList: RecyclerView
    private lateinit var recommendedAdapter: DishListAdapter

    private lateinit var bestGroup: Group
    private lateinit var rvBestList: RecyclerView
    private lateinit var bestAdapter: DishListAdapter

    private lateinit var popularGroup: Group
    private lateinit var rvPopularList: RecyclerView
    private lateinit var popularAdapter: DishListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater)
        val view = binding.root

        val customToolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)
        val activity = activity as RootActivity
        activity.setSupportActionBar(customToolbar)

        bindContentViews(view)

        drawerLayout = view.findViewById(R.id.drawer_layout) as DrawerLayout
        drawerToggle = object : ActionBarDrawerToggle(
            activity,  /* host Activity */
            drawerLayout,  /* DrawerLayout object */
            customToolbar,  /* nav drawer icon to replace 'Up' caret */
            R.string.main_drawer_open,  /* "open drawer" description */
            R.string.main_drawer_close /* "close drawer" description */
        ) {

        }
        bindMenuItems(view)

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

        viewModel.menuItemClicks().observe(viewLifecycleOwner, Observer { item ->
            if (item != null) {
                val activity = activity as RootActivity
                val navController = activity.navController
                drawerLayout.closeDrawer(Gravity.LEFT)

                when (item) {
                    MENU -> navController.navigate(R.id.action_nav_main_to_nav_menu)
                    ABOUT -> navController.navigate(R.id.action_nav_main_to_nav_about)
                    else -> {} /* */
                }
            }
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

    private fun bindContentViews(view: View) {
        val mainBinding = FragmentMainContentBinding.bind(view.findViewById(R.id.fragment_main_content))
        recommendedGroup = mainBinding.recommendedGroup
        rvRecommendedList = mainBinding.rvRecommendedList
        recommendedAdapter = createDishAdapter()
        rvRecommendedList.adapter = recommendedAdapter

        bestGroup = mainBinding.bestGroup
        rvBestList = mainBinding.rvBestList
        bestAdapter = createDishAdapter()
        rvBestList.adapter = bestAdapter

        popularGroup = mainBinding.popularGroup
        rvPopularList = mainBinding.rvPopularList
        popularAdapter = createDishAdapter()
        rvPopularList.adapter = popularAdapter
    }

    private fun bindMenuItems(view: View) {
        val menuBinding = NavMenuMainBinding.bind(view.findViewById(R.id.nav_menu_main))
        navHeaderGroup = menuBinding.navHeaderGroup

        menuBinding.navMainItem.bind(MAIN)
        menuBinding.navMenuItem.bind(MENU)
        menuBinding.navFavoriteItem.bind(FAVORITE)
        menuBinding.navCartItem.bind(CART)
        menuBinding.navProfileItem.bind(PROFILE)
        menuBinding.navOrdersItem.bind(ORDERS)
        menuBinding.navNotificationsItem.bind(NOTIFICATIONS)

        menuBinding.navAbout.bind(ABOUT)
    }

    private fun MainMenuItemView.bind(menuItem: MainMenuItem) {
        setOnClickListener { viewModel.handleMainItemClick(menuItem) }
    }

    private fun createDishAdapter(): DishListAdapter {
        return DishListAdapter(
            { dish -> viewModel.handleAddClick(dish) },
            { dish -> viewModel.handleDishClick(dish) }
        )
    }

}
