package com.thoughtworks.android.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class UiUtils {

    companion object {

        fun replaceFragmentAndAddToBackStack(
            fragmentManager: FragmentManager,
            fragment: Fragment, fragmentId: Int, tag: String?
        ) {
            val beginTransaction = fragmentManager.beginTransaction()
            beginTransaction.replace(fragmentId, fragment, tag)
            beginTransaction.addToBackStack(tag)
            beginTransaction.commitAllowingStateLoss()
        }
    }

}