package com.thoughtworks.android.ui

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.thoughtworks.android.R
import com.thoughtworks.android.model.Data

class ContactActivity : AppCompatActivity() {

    companion object {
        const val CONTACT = "contact"
        const val SEND_MAIN = "send_main"
        const val RESULT_CODE = 200
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contact_layout)
        receiveData()
    }

    private fun receiveData() {
        val contactData = intent.getParcelableExtra<Data>(CONTACT)
        if (contactData?.name != null && contactData.phone != null) {
            val receiveName = findViewById<TextView>(R.id.receive_name)
            val receivePhone = findViewById<TextView>(R.id.receive_phone)
            receiveName.text = contactData.name
            receivePhone.text = contactData.phone
            sendData(contactData.name, contactData.phone)
        }
    }

    private fun sendData(name : String?, phone : String?) {
        val intent =  Intent(this, ContactActivity::class.java)
            val data = Data(name, phone)
            intent.putExtra(SEND_MAIN, data)
            setResult(RESULT_CODE, intent)
    }

}