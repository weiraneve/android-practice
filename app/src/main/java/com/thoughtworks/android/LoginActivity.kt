package com.thoughtworks.android

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }
}