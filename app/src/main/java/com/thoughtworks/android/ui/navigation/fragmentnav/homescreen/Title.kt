package com.thoughtworks.android.ui.navigation.fragmentnav.homescreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import com.thoughtworks.android.R

class Title : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_title, container, false)

        navAbout(view)

        val request =
            NavDeepLinkRequest.Builder.fromUri("android-app://thoughtworks.com/deeplink".toUri())
                .build()
        navDeepLink(view, request)
        return view
    }

    private fun navAbout(view: View) {
        view.findViewById<Button>(R.id.title_btn_about).setOnClickListener {
            findNavController().navigate(R.id.action_title_to_about)
        }
    }

    private fun navDeepLink(view: View, request: NavDeepLinkRequest) {
        view.findViewById<Button>(R.id.title_btn_deeplink).setOnClickListener {
            findNavController().navigate(request)
        }
    }
}
