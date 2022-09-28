package com.thoughtworks.android.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.thoughtworks.android.PracticeApp
import com.thoughtworks.android.R
import com.thoughtworks.android.utils.Dependency

class SharedPreferenceActivity : AppCompatActivity() {

    private val spInfo: TextView by lazy { findViewById(R.id.sp_info) }
    private val buttonSp: Button by lazy { findViewById(R.id.button_sp) }
    private lateinit var dependency: Dependency

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sp_layout)
        initialize()
    }

    private fun initialize() {
        val practiceApp = application as PracticeApp
        dependency =  practiceApp.getDependency()
        buttonSp.setOnClickListener {
            dependency.dataSource.isHintShown = true
            initStatus()
        }
        initStatus()
    }

    private fun initStatus() {
        spInfo.setText(
            if (dependency.dataSource.isHintShown) R.string.welcome_back else R.string.sp_tips
        )
        buttonSp.visibility = if (dependency.dataSource.isHintShown) View.GONE else View.VISIBLE
    }
}