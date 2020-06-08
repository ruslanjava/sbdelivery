package ru.skillbranch.sbdelivery.ui.screens.main

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.MaterialToolbar
import ru.skillbranch.sbdelivery.R
import ru.skillbranch.sbdelivery.RootActivity
import ru.skillbranch.sbdelivery.databinding.FragmentMainBinding


class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var drawerToggle: ActionBarDrawerToggle

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(inflater)
        val view = binding.root

        val customToolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)
        val activity = activity as RootActivity
        activity.setSupportActionBar(customToolbar)

        drawerLayout = view.findViewById(R.id.drawer_layout) as DrawerLayout
        drawerToggle = object : ActionBarDrawerToggle(
            activity,  /* host Activity */
            drawerLayout,  /* DrawerLayout object */
            customToolbar,  /* nav drawer icon to replace 'Up' caret */
            R.string.main_drawer_open,  /* "open drawer" description */
            R.string.main_drawer_close /* "close drawer" description */
        ) {

        }

        setHasOptionsMenu(true)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel

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

}
