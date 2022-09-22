package com.thoughtworks.android.ui.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_fragment, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.action_android -> {
                displayAndroid()
                true
            }
            R.id.action_java -> {
                displayJava()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
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
            DisplayAndroidFragment(),
            R.id.content
        )
    }

    private fun displayJava() {
        UiUtils.addOrShowFragmentAndHideOthers(
            supportFragmentManager,
            DisplayJavaFragment(),
            R.id.content
        )
    }

}