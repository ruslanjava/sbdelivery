package ru.skillbranch.sbdelivery.ui.screens.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import ru.skillbranch.sbdelivery.R
import ru.skillbranch.sbdelivery.databinding.FragmentCategoryBinding
import ru.skillbranch.sbdelivery.orm.entities.dishes.Category
import ru.skillbranch.sbdelivery.ui.dishList.DishListAdapter
import ru.skillbranch.sbdelivery.ui.screens.RootActivity
import ru.skillbranch.sbdelivery.ui.screens.dish.DishFragmentArgs
import ru.skillbranch.sbdelivery.ui.screens.category.subCategoryList.SubCategoryListAdapter

class CategoryFragment : Fragment() {

    private val viewModel: CategoryViewModel by viewModels()

    private lateinit var titleView: AppCompatTextView

    private lateinit var subCategoryList: RecyclerView
    private lateinit var subCategoryAdapter: SubCategoryListAdapter

    private lateinit var dishList: RecyclerView
    private lateinit var dishAdapter: DishListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentCategoryBinding.inflate(inflater)

        titleView = binding.dishFragmentTitle

        subCategoryList = binding.rvSubCategoryList
        subCategoryAdapter = SubCategoryListAdapter { subCategory ->
            val activity = activity as RootActivity
            val navController = activity.navController
            val arguments = CategoryFragmentArgs(subCategory.id)
            navController.navigate(R.id.action_nav_category_self, arguments.toBundle())
        }
        subCategoryList.adapter = subCategoryAdapter

        dishList = binding.rvDishList
        dishAdapter = DishListAdapter(
            R.layout.view_list_item_dish_grid,
            { dish -> viewModel.handleAddClick(dish) },
            { dish -> viewModel.handleFavoriteClick(dish) },
            { dish -> viewModel.handleDishClick(dish) }
        )
        dishList.adapter = dishAdapter

        val customToolbar = binding.toolbar
        val activity = activity as RootActivity
        activity.setSupportActionBar(customToolbar)

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val categoryArgs = CategoryFragmentArgs.fromBundle(requireArguments())
        val categoryId = categoryArgs.categoryId

        viewModel.category(categoryId).observe(viewLifecycleOwner, Observer { category ->
            if (category == Category.SALES) {
                titleView.text = getString(R.string.menu_sales)
            } else {
                titleView.text = category?.name
            }
        })

        viewModel.subCategories(categoryId).observe(viewLifecycleOwner, Observer {  subCategories ->
            subCategoryAdapter.updateItems(subCategories)
        })

        viewModel.categoryDishes(categoryId).observe(viewLifecycleOwner, Observer { dishes ->
            dishAdapter.updateItems(dishes)
        })

        viewModel.clickedDishes().observe(viewLifecycleOwner, Observer { dish ->
            dish?.let {
                val activity = activity as RootActivity
                val navController = activity.navController
                val dishArgs = DishFragmentArgs(it.id)
                navController.navigate(R.id.action_nav_category_to_nav_dish, dishArgs.toBundle())
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