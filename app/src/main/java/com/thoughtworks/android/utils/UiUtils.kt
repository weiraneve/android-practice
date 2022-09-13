package com.thoughtworks.android.utils

import android.app.Activity
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.thoughtworks.android.R

class UiUtils {

    companion object {

        fun addOrShowFragmentAndHideOthers(
            fragmentManager: FragmentManager,
            fragment: Fragment, fragmentId: Int, tag: String?
        ) {
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

        fun customToast(activity: Activity, text: String, @DrawableRes drawable: Int, duration: Int): Toast {
            val inflater: LayoutInflater = activity.layoutInflater
            val layout: View = inflater.inflate(
                R.layout.toast,
                activity.findViewById(R.id.toast_layout_root) as ViewGroup?
            )

            val image: ImageView = layout.findViewById(R.id.image) as ImageView
            image.setImageResource(drawable)
            (layout.findViewById(R.id.text) as TextView).text = text

            val toast = Toast(activity.applicationContext)
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
            toast.duration = duration
            toast.view = layout

            return toast
        }

    }
}