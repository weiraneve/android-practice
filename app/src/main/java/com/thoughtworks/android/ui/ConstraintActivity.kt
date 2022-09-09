package com.thoughtworks.android.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.thoughtworks.android.R

class ConstraintActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.constraint_layout)
    }
}