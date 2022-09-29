package com.thoughtworks.android

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.thoughtworks.android.ui.ConstraintActivity
import com.thoughtworks.android.ui.LoginActivity

class MainActivity : AppCompatActivity() {

    private lateinit var dialog: Dialog
    private val buttonContainer: LinearLayout by lazy { findViewById(R.id.button_container) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()
    }

    private fun initUI() {
        generateButtons()
    }

    private val pickContactLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val data = it.data
                val uri: Uri? = data?.data
                val contact = getPhoneContacts(uri)
                if (contact != null) {
                    val name = contact[0]
                    val number = contact[1]
                    showDialog("$name\n$number")
                }
            }
        }

    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {}

    @SuppressLint("Range")
    private fun getPhoneContacts(uri: Uri?): Array<String?>? {
        val contact = arrayOfNulls<String>(2)
        val contentResolver = contentResolver
        val projection: Array<String> = arrayOf(
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )
        val cursor =
            uri?.let { contentResolver.query(it, projection, null, null, null) }
        if (cursor != null && cursor.moveToFirst()) {
            val columnIndex: Int =
                cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
            contact[0] = cursor.getString(columnIndex)
            contact[1] =
                cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            cursor.close()
        } else {
            return null
        }
        return contact
    }

    private fun showDialog(msg: String) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(msg).setPositiveButton(R.string.ok) { _, _ -> dialog.cancel() }
            .setCancelable(false)
        dialog = builder.create()
        dialog.show()
    }

    private fun generateButtons() {
        addButton(getString(R.string.constraint_layout)) {
            startActivity(Intent(this, ConstraintActivity::class.java))
        }

        addButton(getString(R.string.login)) {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        addButton(getString(R.string.pick_contact)) {
            if (canReadContact()) {
                initContactUI()
            } else {
                permissionLauncher.launch(Manifest.permission.READ_CONTACTS)
            }
        }

        addButton(getString(R.string.button_4))
        addButton(getString(R.string.button_5))
        addButton(getString(R.string.button_6))
        addButton(getString(R.string.button_7))
        addButton(getString(R.string.button_8))
        addButton(getString(R.string.button_9))
        addButton(getString(R.string.button_10))
    }

    private fun initContactUI() {
        val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI).apply {
            type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
        }
        pickContactLauncher.launch(intent)
    }

    private fun addButton(name: String, onClickListener: View.OnClickListener? = null) {
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(0, resources.getDimensionPixelSize(R.dimen.dimen_24), 0, 0)

        val button = Button(this)
        button.layoutParams = layoutParams
        button.text = name
        button.isAllCaps = false

        onClickListener?.let {
            button.setOnClickListener(it)
        }

        buttonContainer.addView(button)
    }

    private fun canReadContact(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED
    }

}