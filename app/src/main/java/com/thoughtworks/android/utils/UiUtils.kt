package com.thoughtworks.android.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

object UiUtils {

    fun addOrShowFragmentAndHideOthers(
        fragmentManager: FragmentManager,
        fragment: Fragment, fragmentId: Int
    ) {
        val transaction = fragmentManager.beginTransaction()
        fragmentManager.fragments.forEach {
            transaction.hide(it)
        }
        showFragment(fragment, transaction)
        addFragment(fragment, transaction, fragmentId)
        transaction.commit()
    }

    private fun showFragment(fragment: Fragment, transaction: FragmentTransaction) {
        if (fragment.isAdded) {
            transaction.show(fragment)
        }
    }

    private fun addFragment(fragment: Fragment, transaction: FragmentTransaction, fragmentId: Int) {
        if (!fragment.isAdded) {
            transaction.add(fragmentId, fragment)
        }
    }

}