package com.thoughtworks.android.ui.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.thoughtworks.android.R
import com.thoughtworks.android.utils.UiUtils

class MyFragmentActivity : AppCompatActivity() {

    private val buttonAndroid: Button by lazy { findViewById(R.id.button_android) }
    private val buttonJava: Button by lazy { findViewById(R.id.button_java) }
    private val displayAndroidFragment = DisplayAndroidFragment()
    private val displayJavaFragment = DisplayJavaFragment()

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
        UiUtils.addOrShowFragmentAndHideOthers(
            supportFragmentManager,
            displayAndroidFragment,
            R.id.content,
            null
        )
    }

    private fun displayJava() {
        UiUtils.addOrShowFragmentAndHideOthers(
            supportFragmentManager,
            displayJavaFragment,
            R.id.content,
            null
        )
    }

}