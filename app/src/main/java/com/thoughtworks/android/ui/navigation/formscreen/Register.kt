package com.thoughtworks.android.ui.navigation.formscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.thoughtworks.android.R
import com.thoughtworks.android.ui.navigation.BaseNavFragment

class Register : Fragment(), BaseNavFragment {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onBackPressed(): Boolean = false
}
