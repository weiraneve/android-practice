package com.thoughtworks.android.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class UiUtils {

    companion object {

        fun addOrShowFragmentAndHideOthers(fragmentManager: FragmentManager,
                              fragment: Fragment, fragmentId: Int, tag: String?) {
            val transaction = fragmentManager.beginTransaction()
            for (otherFragment in fragmentManager.fragments) {
                transaction.hide(otherFragment)
            }
            if (fragment.isAdded) {
                transaction.show(fragment)
            } else {
                transaction.add(fragmentId, fragment, tag)
                println(fragment)
            }
            transaction.commit()
        }

    }
}