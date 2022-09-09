package com.thoughtworks.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MyFragmentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.my_fragment_layout)
    }
}