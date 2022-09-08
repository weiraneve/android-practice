package com.thoughtworks.android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.thoughtworks.android.model.Data

class MainActivity : AppCompatActivity() {

    companion object {
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

        val startActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode  == ContactActivity.RESULT_CODE && it.data!= null) {
                val info = it.data?.getParcelableExtra<Data>(ContactActivity.SEND_MAIN)
                Toast.makeText(this, "${info?.name} : ${info?.phone}", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this,"无法获取数据",Toast.LENGTH_SHORT).show()

            }
        }
        button.setOnClickListener {
            val intent =  Intent(this, ContactActivity::class.java).apply {
                val data = Data("jack", "139000000000")
                putExtra(ContactActivity.CONTACT, data)
            }
            startActivity.launch(intent)
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