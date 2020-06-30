package ru.skillbranch.sbdelivery.ui.screens.dish

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.skillbranch.sbdelivery.databinding.FragmentDishBinding
import ru.skillbranch.sbdelivery.http.data.review.ReviewRes
import ru.skillbranch.sbdelivery.orm.entities.dishes.Dish
import ru.skillbranch.sbdelivery.ui.screens.RootActivity

class DishFragment : Fragment() {

    private val viewModel: DishViewModel by viewModels()
    private val args: DishFragmentArgs by navArgs()

    private lateinit var tvDishFragmentTitle: AppCompatTextView
    private lateinit var ivDishLike: AppCompatImageView
    private lateinit var ivDishSale: AppCompatTextView

    private lateinit var ivDishBackground: AppCompatImageView
    private lateinit var tvDishTitle: AppCompatTextView
    private lateinit var tvDishDescription: AppCompatTextView
    private lateinit var tvDishOldPrice: AppCompatTextView
    private lateinit var tvDishPrice: AppCompatTextView

    private lateinit var rvDishReviewsArea: ConstraintLayout
    private lateinit var tvDishReviewsRating: AppCompatTextView

    private lateinit var rvDishReviewsList: RecyclerView
    private lateinit var adapter: CommentListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDishBinding.inflate(inflater)
        val view = binding.root

        val customToolbar = binding.toolbar
        val activity = activity as RootActivity
        activity.setSupportActionBar(customToolbar)

        val actionBar = activity.supportActionBar!!
        actionBar.setDisplayShowHomeEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        tvDishFragmentTitle = binding.dishFragmentTitle
        ivDishLike = binding.dishLike
        ivDishSale = binding.dishSale

        ivDishBackground = binding.dishBackground
        tvDishTitle = binding.dishTitle
        tvDishDescription = binding.dishDescription

        tvDishOldPrice = binding.dishOldPrice
        tvDishPrice = binding.dishPrice

        rvDishReviewsArea = binding.dishReviewsArea
        tvDishReviewsRating = binding.dishReviewsRating

        rvDishReviewsList = binding.dishReviewsList
        adapter = CommentListAdapter()
        rvDishReviewsList.adapter = adapter

        setHasOptionsMenu(true)

        return view
    }

    @ExperimentalCoroutinesApi
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.dish(args.dishId).observe(viewLifecycleOwner, Observer { dish ->
            updateViews(dish)
        })
        viewModel.comments(args.dishId).observe(viewLifecycleOwner, Observer { comments ->
            updateComments(comments)
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            val activity = activity as RootActivity
            activity.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateViews(dish: Dish) {
        tvDishFragmentTitle.text = dish.name
        ivDishLike.alpha = if (dish.likes > 0) 1.0f else 0.5f

        Glide.with(requireContext())
            .load(dish.image)
            .into(ivDishBackground)

        tvDishTitle.text = dish.name
        tvDishDescription.text = dish.description

        if (dish.oldPrice != null) {
            ivDishSale.visibility = View.VISIBLE
            tvDishOldPrice.visibility = View.VISIBLE
            tvDishOldPrice.text = dish.oldPrice.toString()
        } else {
            ivDishSale.visibility = View.GONE
            tvDishOldPrice.visibility = View.GONE
        }
        tvDishPrice.text = dish.price.toString()

        if (dish.rating > 0) {
            rvDishReviewsArea.visibility = View.VISIBLE
            tvDishReviewsRating.text = String.format("%0.1f/5", dish.rating)
        } else {
            rvDishReviewsArea.visibility = View.INVISIBLE
        }
    }

    private fun updateComments(comments: List<ReviewRes>) {
        if (comments.isNotEmpty()) {
            rvDishReviewsList.visibility = View.VISIBLE
            adapter.updateItems(comments)
        } else {
            rvDishReviewsList.visibility = View.GONE
        }
    }

}