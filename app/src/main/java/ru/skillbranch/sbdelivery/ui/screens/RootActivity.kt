package ru.skillbranch.sbdelivery.ui.screens

import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.skillbranch.sbdelivery.R
import ru.skillbranch.sbdelivery.databinding.ActivityRootBinding

class RootActivity : DaggerAppCompatActivity() {

    lateinit var navController: NavController

    private lateinit var rootContainer: ViewGroup
    private val viewModel: RootViewModel by viewModels()

    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityRootBinding.inflate(layoutInflater)
        rootContainer = binding.rootContainer
        setContentView(binding.root)

        androidInjector().inject(viewModel)

        navController = Navigation.findNavController(this,
            R.id.nav_host_fragment
        )

        val firstStart = savedInstanceState == null
        if (firstStart) {
            loadData()
        }
    }

    @ExperimentalCoroutinesApi
    private fun loadData() {
        viewModel.syncDataIfNeed().observe(this, Observer { result ->
            when (result) {
                is LoadResult.Loading -> {
                    navController.navigate(R.id.nav_splash)
                }

                is LoadResult.Success -> {
                    navController.navigate(R.id.action_nav_splash_to_nav_main)
                }

                is LoadResult.Error -> {
                    Snackbar.make(
                        rootContainer, result.errorMessage.toString(), Snackbar.LENGTH_INDEFINITE
                    ).show()
                }
            }
        })
    }

    override fun onBackPressed() {
        navController.popBackStack()
    }

}
