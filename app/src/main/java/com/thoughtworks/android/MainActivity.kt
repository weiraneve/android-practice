package com.thoughtworks.android

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val constraintButton = findViewById<Button>(R.id.constraint)
        constraintButton!!.setOnClickListener { openConstraintActivity() }

        val loginButton = findViewById<Button>(R.id.login)
        loginButton!!.setOnClickListener { openLoginActivity() }

    }

    private fun openLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun openConstraintActivity() {
        val intent =  Intent(this, ConstraintActivity::class.java)
        startActivity(intent)
    }

}