package com.thoughtworks.android.common.activity

import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import com.thoughtworks.android.R

open class BaseActivity : AppCompatActivity() {

    private lateinit var linearLayout: LinearLayout

    private val layoutParams = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    )

    fun setContentView() {
        val scrollView = ScrollView(this)
        linearLayout = LinearLayout(this)
        linearLayout.orientation = LinearLayout.VERTICAL
        scrollView.addView(linearLayout)
        addContentView(scrollView, layoutParams)
    }

    fun <T : AppCompatActivity> generateButtonAndUI(entryInt: Int, java: Class<T>) {
        addButton(getString(entryInt)) {
            startActivity(Intent(this, java))
        }
    }

    private fun addButton(name: String, onClickListener: View.OnClickListener? = null) {
        layoutParams.setMargins(0, resources.getDimensionPixelSize(R.dimen.dimen_24), 0, 0)
        val button = Button(this)
        button.layoutParams = layoutParams
        button.text = name
        button.isAllCaps = false
        onClickListener?.let {
            button.setOnClickListener(it)
        }
        linearLayout.addView(button)
    }
}