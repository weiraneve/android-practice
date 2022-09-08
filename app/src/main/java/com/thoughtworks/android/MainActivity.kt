package com.thoughtworks.android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val mainActivity = "MainActivity"
    private val create = "onCreate"
    private val start = "onStart"
    private val resume = "onResume"
    private val pause = "onPause"
    private val stop = "onStop"
    private val destroy = "onDestroy"
    private val space = "========================================"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(mainActivity, create + space)

        openConstraintActivity()
        openLoginActivity()
        openContactActivity()
    }

    override fun onStart() {
        super.onStart()
        Log.i(mainActivity, start + space)
    }

    override fun onResume() {
        super.onResume()
        Log.i(mainActivity, resume + space)
    }

    override fun onPause() {
        super.onPause()
        Log.i(mainActivity, pause + space)
    }

    override fun onStop() {
        super.onStop()
        Log.i(mainActivity, stop + space)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(mainActivity, destroy + space)
    }

    private fun openLoginActivity() {
        val loginButton = findViewById<Button>(R.id.login)
        loginButton!!.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun openConstraintActivity() {
        val constraintButton = findViewById<Button>(R.id.constraint)
        constraintButton!!.setOnClickListener {
            val intent =  Intent(this, ConstraintActivity::class.java)
            startActivity(intent)
        }
    }

    private fun openContactActivity() {
        val contactButton = findViewById<Button>(R.id.contact)
        contactButton!!.setOnClickListener {
            val intent =  Intent("com.thoughtworks.android.action.0")
            startActivity(intent)
        }
    }

}