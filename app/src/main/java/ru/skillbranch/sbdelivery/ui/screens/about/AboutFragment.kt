package ru.skillbranch.sbdelivery.ui.screens.about

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import ru.skillbranch.sbdelivery.databinding.FragmentAboutBinding
import ru.skillbranch.sbdelivery.ui.screens.RootActivity

class AboutFragment : Fragment() {

    private val viewModel: AboutViewModel by viewModels()
    private lateinit var aboutVersionValue: AppCompatTextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAboutBinding.inflate(inflater)
        aboutVersionValue = binding.aboutVersionValue

        val customToolbar = binding.toolbar
        val activity = activity as RootActivity
        activity.setSupportActionBar(customToolbar)

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            findNavController().navigateUp()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.aboutVersion().observe(viewLifecycleOwner, Observer { version ->
            updateAboutViews(version)
        })
    }

    private fun updateAboutViews(version: String) {
        aboutVersionValue.text = version
    }

}