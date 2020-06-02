package ru.skillbranch.sbdelivery.ui.screens.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import ru.skillbranch.sbdelivery.R
import ru.skillbranch.sbdelivery.databinding.FragmentSplashBinding
import ru.skillbranch.sbdelivery.extensions.attrColor

class SplashFragment: Fragment() {

    private lateinit var splashBackground: View
    private lateinit var viewModel: SplashViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSplashBinding.inflate(inflater)
        splashBackground = binding.splashBackground
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context = requireContext()
        splashBackground.background = TilingDrawable(
            AppCompatResources.getDrawable(context, R.drawable.ic_splash_tile)!!,
            context.attrColor(R.attr.windowBackground)
        )

        viewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)
        viewModel.getObservableGallery().observe(viewLifecycleOwner, Observer { complete ->
            // TODO
        })
    }

}