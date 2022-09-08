package com.thoughtworks.android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.thoughtworks.android.model.Data

class MainActivity : AppCompatActivity() {

    companion object {
        private const val REQUEST_CODE = 201
        private const val mainActivity = "MainActivity"
        private const val create = "onCreate"
        private const val space = "========================================"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(mainActivity, create + space)

        openConstraintActivity()
        openLoginActivity()
        openContactActivity()
    }

    private fun openContactActivity() {
        val button = findViewById<Button>(R.id.contact)
        button.setOnClickListener {
            val intent =  Intent(this, ContactActivity::class.java).apply {
                val data = Data("jack", "139000000000")
                putExtra(ContactActivity.CONTACT, data)
            }
            startActivityForResult(intent, REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode  == ContactActivity.RESULT_CODE && data!= null) {
            val info = data.getParcelableExtra<Data>(ContactActivity.SEND_MAIN)
            Toast.makeText(this, "${info?.name} : ${info?.phone}", Toast.LENGTH_SHORT).show()
        }
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

}