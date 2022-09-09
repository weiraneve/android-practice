package com.thoughtworks.android

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.IdRes
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

        openContactActivity()
        addListener(R.id.fragment, MyFragmentActivity::class.java)
        addListener(R.id.login, LoginActivity::class.java)
        addListener(R.id.constraint, ConstraintActivity::class.java)
    }

    private fun <T : Activity> addListener(@IdRes id: Int, className: Class<T>) {
        findViewById<View>(id).setOnClickListener {
            val intent = Intent(this, className)
            startActivity(intent)
        }
    }

    private fun openContactActivity() {
        val button = findViewById<Button>(R.id.contact)

        val startActivity =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == ContactActivity.RESULT_CODE && it.data != null) {
                    val info = it.data?.getParcelableExtra<Data>(ContactActivity.SEND_MAIN)
                    Toast.makeText(this, "${info?.name} : ${info?.phone}", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(this, "无法获取数据", Toast.LENGTH_SHORT).show()

                }
         }
        button.setOnClickListener {
            val intent = Intent(this, ContactActivity::class.java).apply {
                val data = Data("jack", "139000000000")
                putExtra(ContactActivity.CONTACT, data)
            }
            startActivity.launch(intent)
        }
    }

}