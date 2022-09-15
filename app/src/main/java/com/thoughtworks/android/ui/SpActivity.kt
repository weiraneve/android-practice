package com.thoughtworks.android.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.thoughtworks.android.R
import com.thoughtworks.android.data.source.DataSource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SpActivity : AppCompatActivity() {
    
    @Inject
    lateinit var dataSource: DataSource

    private val spInfo: TextView by lazy { findViewById(R.id.sp_info) }
    private val buttonSp: Button by lazy { findViewById(R.id.button_sp) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sp_layout)
        initialize()
    }

    private fun initialize() {
        buttonSp.setOnClickListener {
            dataSource.isKnown = true
            refreshStatus()
        }
        refreshStatus()
    }

    private fun refreshStatus() {
        spInfo.setText(
            if (dataSource.isKnown) R.string.welcome_back else R.string.sp_tips
        )
        buttonSp.visibility = if (dataSource.isKnown) View.GONE else View.VISIBLE
    }
}
