package com.thoughtworks.android.ui.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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
        val toolbar = view.findViewById<Toolbar>(R.id.bottom_nav_toolbar)
        addToolbarListener(toolbar)
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