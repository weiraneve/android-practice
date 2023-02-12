package com.thoughtworks.android.ui.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.thoughtworks.android.R

class BottomNavFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_nav, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBottomNavBar(view)
    }

    private fun setupBottomNavBar(view: View) {
        val bottomNavView = view.findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        val toolbar = view.findViewById<Toolbar>(R.id.bottom_nav_toolbar)
        // Your navGraphIds must have the same ids as your menuItem ids
        val navGraphIds = listOf(R.navigation.home, R.navigation.list, R.navigation.form)

        addToolbarListener(toolbar)

        val controller = bottomNavView.setupWithNavController(
            fragmentManager = childFragmentManager,
            navGraphIds = navGraphIds,
            backButtonBehaviour = BackButtonBehaviour.POP_HOST_FRAGMENT,
            containerId = R.id.bottom_nav_container,
            firstItemId = R.id.home, // Must be the same as bottomNavSelectedItemId
            intent = requireActivity().intent
        )

        controller.observe(viewLifecycleOwner) { navController ->
            NavigationUI.setupWithNavController(toolbar, navController)
        }
    }

    private fun addToolbarListener(toolbar: Toolbar) {
        toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.settings -> {
                    findNavController().navigate(R.id.settings)
                    true
                }

                else -> false
            }
        }
    }

}