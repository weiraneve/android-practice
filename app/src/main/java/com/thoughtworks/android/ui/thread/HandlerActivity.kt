package com.thoughtworks.android.ui.thread

import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.thoughtworks.android.R
import com.thoughtworks.android.utils.UiUtil

class HandlerActivity : AppCompatActivity() {

    companion object {
        private const val HANDLER_THREAD_NAME = "handlerThread"
        private const val MSG_1 = 1
        private const val MSG_INFO1 = "MSG_1"
        private const val MSG_2 = 2
        private const val MSG_INFO2 = "MSG_2"
    }

    private lateinit var workerHandler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.handler_layout)
        initUI()
        initHandler()
    }

    private fun initUI() {
        val buttonMsg1 = findViewById<Button>(R.id.button_msg1)
        val buttonMsg2 = findViewById<Button>(R.id.button_msg2)
        buttonMsg1.setOnClickListener {
            workerHandler.sendMessage(Message.obtain().apply {
                what = MSG_1
                obj = MSG_INFO1
            })
        }

        buttonMsg2.setOnClickListener {
            workerHandler.sendMessage(Message.obtain().apply {
                what = MSG_2
                obj = MSG_INFO2
            })
        }
    }

    private fun initHandler() {
        val handlerThread = HandlerThread(HANDLER_THREAD_NAME)
        handlerThread.start()
        workerHandler = object : Handler(handlerThread.looper) {
            override fun handleMessage(msg: Message) {
                when (msg.what) {
                    MSG_1, MSG_2 -> showMsgBody(msg)
                }
            }
        }
    }

    private fun showMsgBody(msg: Message) {
        val content = msg.obj as String
        runOnUiThread { UiUtil.showSnackBar(this, content, R.drawable.fb, Snackbar.LENGTH_SHORT).show() }
    }

}