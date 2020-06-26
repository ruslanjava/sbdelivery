package ru.skillbranch.sbdelivery.ui.screens.about

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.Observer
import ru.skillbranch.sbdelivery.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {

    private lateinit var viewModel: AboutViewModel
    private lateinit var aboutVersionValue: AppCompatTextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAboutBinding.inflate(inflater)
        aboutVersionValue = binding.aboutVersionValue
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AboutViewModel::class.java)
        viewModel.aboutVersion().observe(viewLifecycleOwner, Observer { version ->
            aboutVersionValue.text = version
        })
    }

}