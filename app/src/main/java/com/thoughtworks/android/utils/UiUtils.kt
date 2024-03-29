package com.thoughtworks.android.utils

import android.app.Activity
import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.snackbar.Snackbar
import com.thoughtworks.android.R

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
            transaction.replace(fragmentId, fragment)
        }
    }

    fun customToast(activity: Activity, text: String, @DrawableRes drawable: Int, duration: Int): Toast {
        val inflater: LayoutInflater = activity.layoutInflater
        val layout: View = inflater.inflate(
            R.layout.handler_toast,
            activity.findViewById(R.id.handler_toast_layout_root) as ViewGroup?
        )

        val image: ImageView = layout.findViewById(R.id.handler_toast_image) as ImageView
        image.setImageResource(drawable)
        (layout.findViewById(R.id.handler_toast_text) as TextView).text = text

        val toast = Toast(activity.applicationContext)
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
        toast.duration = duration
        toast.view = layout

        return toast
    }

    fun showSnackBar(
        activity: Activity,
        text: String,
        @DrawableRes drawable: Int,
        duration: Int
    ): Snackbar {
        val layoutHandler: View = activity.findViewById(R.id.handler)
        val snackBar = Snackbar.make(layoutHandler, text, duration)
        val viewGroup = snackBar.view as ViewGroup
        val linearLayout = LinearLayout(layoutHandler.context)

        // 加载图片
        val imageView = ImageView(layoutHandler.context)
        imageView.setImageResource(drawable)
        linearLayout.addView(imageView)
        imageView.layoutParams = getLayoutParamsWithLinearLayout()
        // 加载文字
        val textView = TextView(layoutHandler.context)
        textView.text = text
        textView.setTextColor(Color.WHITE)
        textView.layoutParams = getLayoutParamsWithLinearLayout()

        linearLayout.addView(textView)
        viewGroup.addView(linearLayout)

        snackBar.view.layoutParams = getLayoutParamsWithFrameLayout()

        return snackBar
    }

    private fun getLayoutParamsWithFrameLayout(): FrameLayout.LayoutParams {
        val layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.gravity = Gravity.CENTER
        return layoutParams
    }

    private fun getLayoutParamsWithLinearLayout(): LinearLayout.LayoutParams {
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.gravity = Gravity.CENTER
        return layoutParams
    }

}