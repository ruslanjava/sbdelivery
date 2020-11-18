package ru.skillbranch.sbdelivery.ui.screens.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import ru.skillbranch.sbdelivery.databinding.FragmentSplashBinding

class SplashFragment: Fragment() {

    private lateinit var ivSplashLogo: AppCompatImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSplashBinding.inflate(inflater)
        ivSplashLogo = binding.splashLogo
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val animation = AlphaAnimation(0.4f, 0.8f)
        animation.duration = 1000
        animation.fillAfter = true
        animation.repeatMode = Animation.REVERSE
        animation.repeatCount = Animation.INFINITE
        ivSplashLogo.startAnimation(animation)
    }

}