package com.thoughtworks.android.ui.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.thoughtworks.android.R
import com.thoughtworks.android.utils.UiUtils

class MyFragmentActivity : AppCompatActivity() {

    private val buttonAndroid: Button by lazy { findViewById(R.id.button_android) }
    private val buttonJava: Button by lazy { findViewById(R.id.button_java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_fragment_layout)
        initUI()
    }

    private fun initUI() {
        buttonAndroid.setOnClickListener {
            displayAndroid()
        }

        buttonJava.setOnClickListener {
            displayJava()
        }

        displayAndroid()
    }

    private fun displayAndroid() {
        UiUtils.replaceFragmentAndAddToBackStack(
            supportFragmentManager,
            DisplayAndroidFragment(),
            R.id.content,
            null
        )
    }

    private fun displayJava() {
        UiUtils.replaceFragmentAndAddToBackStack(
            supportFragmentManager,
            DisplayJavaFragment(),
            R.id.content,
            null
        )
    }

    override fun onBackPressed() {
        finish()
    }

}