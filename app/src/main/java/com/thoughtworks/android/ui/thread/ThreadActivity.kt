package com.thoughtworks.android.ui.thread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import com.thoughtworks.android.R
import java.util.*
import java.util.concurrent.Executors

class ThreadActivity : AppCompatActivity() {

    companion object {
        private const val SECOND_DURATION = 1000L
    }

    private val buttonCount: Button by lazy { findViewById(R.id.button_count) }
    private val executor = Executors.newSingleThreadExecutor()

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
        executor.execute {
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
        }
    }

}