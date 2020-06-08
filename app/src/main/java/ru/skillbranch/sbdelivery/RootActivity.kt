package ru.skillbranch.sbdelivery

import android.app.Application
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import ru.skillbranch.sbdelivery.databinding.ActivityRootBinding
import ru.skillbranch.sbdelivery.ui.screens.splash.SplashFragment

class RootActivity : AppCompatActivity() {

    lateinit var rootContainer: ViewGroup
    lateinit var navController: NavController
    private lateinit var viewModel: RootViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityRootBinding.inflate(layoutInflater)
        rootContainer = binding.rootContainer
        setContentView(binding.root)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        initViewModel()

        val firstStart = savedInstanceState == null
        if (firstStart) {
            loadData()
        }
    }

    private fun loadData() {
        viewModel.syncDataIfNeed().observe(this, Observer<LoadResult<Boolean>> {
            when (it) {

                is LoadResult.Loading -> {
                    navController.navigate(R.id.nav_splash)
                }

                is LoadResult.Success -> {
                    navController.navigate(R.id.action_nav_splash_to_nav_main)
                }

                is LoadResult.Error -> {
                    Snackbar.make(
                        rootContainer, it.errorMessage.toString(), Snackbar.LENGTH_INDEFINITE
                    ).show()
                }

            }
        })
    }


    private fun initViewModel() {
        val vmFactory = RootViewModelFactory(this.application)
        viewModel = ViewModelProviders.of(this, vmFactory).get(RootViewModel::class.java)
    }

    class RootViewModelFactory(private val app: Application) : ViewModelProvider.Factory {

        override fun <T: ViewModel> create(modelClass: Class<T>) : T {
            if (modelClass.isAssignableFrom(RootViewModel::class.java)) {
                return RootViewModel(app) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }

    }

}
