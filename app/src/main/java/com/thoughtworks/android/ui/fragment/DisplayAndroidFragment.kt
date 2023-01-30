package com.thoughtworks.android.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.thoughtworks.android.R

class DisplayAndroidFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, instanceState: Bundle?) : View? {
        return inflater.inflate(R.layout.fragment_layout_android, container, false)
    }

}