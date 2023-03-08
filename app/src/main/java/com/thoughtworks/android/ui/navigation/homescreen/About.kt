package com.thoughtworks.android.ui.navigation.homescreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.thoughtworks.android.R
import com.thoughtworks.android.ui.navigation.BaseNavFragment

class About : Fragment(), BaseNavFragment {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onBackPressed(): Boolean {
        findNavController().popBackStack()
        return true
    }
}
