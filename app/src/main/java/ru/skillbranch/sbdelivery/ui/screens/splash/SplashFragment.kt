package ru.skillbranch.sbdelivery.ui.screens.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.skillbranch.sbdelivery.databinding.FragmentSplashBinding

class SplashFragment: Fragment() {

    private lateinit var splashBackground: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSplashBinding.inflate(inflater)
        return binding.root
    }

}