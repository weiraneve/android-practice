package com.thoughtworks.android.ui.someactivity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.thoughtworks.android.R
import com.thoughtworks.android.data.source.Repository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SharedPreferenceActivity : AppCompatActivity() {
    
    @Inject
    lateinit var repository: Repository

    private val spInfo: TextView by lazy { findViewById(R.id.sp_info) }
    private val buttonSp: Button by lazy { findViewById(R.id.button_sp) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sp)
        initialize()
    }

    private fun initialize() {
        buttonSp.setOnClickListener {
            repository.isHintShown = true
            refreshStatus()
        }
        refreshStatus()
    }

    private fun refreshStatus() {
        spInfo.setText(
            if (repository.isHintShown) R.string.welcome_back else R.string.sp_tips
        )
        buttonSp.visibility = if (repository.isHintShown) View.GONE else View.VISIBLE
    }

}
