package com.thoughtworks.android.ui.thread

import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.thoughtworks.android.R
import java.util.*

class ThreadActivity : AppCompatActivity() {

    companion object {
        private const val SECOND_DURATION = 1000L
    }

    private val buttonCount: Button by lazy { findViewById(R.id.button_count) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.thread_layout)
        initUI()
    }

    private fun initUI() {
        buttonCount.setOnClickListener {
            startCount()
            buttonCount.isEnabled = false
        }
    }

    private fun startCount() {
        Thread {
            var count = 0
            while (count < 10) {
                SystemClock.sleep(SECOND_DURATION)
                count++
                val finalCount = count
                runOnUiThread {
                    buttonCount.text = String.format(
                        Locale.getDefault(),
                        "%d",
                        finalCount
                    )
                }
            }
            runOnUiThread { buttonCount.isEnabled = true }
        }.start()

    }

}