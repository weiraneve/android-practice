package com.thoughtworks.android

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.button0)
        button!!.setOnClickListener { openNewActivity() }

    }

    private fun openNewActivity() {
        val intent =  Intent(this, ConstraintActivity::class.java)
        startActivity(intent)
    }

}