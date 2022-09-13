package com.thoughtworks.android

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.thoughtworks.android.ui.ConstraintActivity
import com.thoughtworks.android.ui.LoginActivity
import com.thoughtworks.android.ui.fragment.MyFragmentActivity
import com.thoughtworks.android.ui.recyclerview.RecyclerViewActivity
import com.thoughtworks.android.ui.thread.HandlerActivity
import com.thoughtworks.android.ui.thread.ThreadActivity

class MainActivity : AppCompatActivity() {

    private lateinit var pickContactLauncher: ActivityResultLauncher<Void?>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private lateinit var dialog: Dialog
    private val buttonContainer: LinearLayout by lazy { findViewById(R.id.button_container) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()
    }

    private fun initUI() {
        generateButtons()
        initContactRetriever()
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
                pickContactLauncher.launch(null)
            } else {
                permissionLauncher.launch(Manifest.permission.READ_CONTACTS)
            }
        }

        addButton(getString(R.string.fragment)) {
            startActivity(Intent(this, MyFragmentActivity::class.java))
        }

        addButton(getString(R.string.recycler_view)) {
            startActivity(Intent(this, RecyclerViewActivity::class.java))
        }

        addButton(getString(R.string.thread)) {
            startActivity(Intent(this, ThreadActivity::class.java))
        }

        addButton(getString(R.string.handler)) {
            startActivity(Intent(this, HandlerActivity::class.java))
        }

        addButton(getString(R.string.button_8))
        addButton(getString(R.string.button_9))
        addButton(getString(R.string.button_10))
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

    private fun initContactRetriever() {
        pickContactLauncher =
            registerForActivityResult(ActivityResultContracts.PickContact()) { uri ->
                val projection = arrayOf(
                    ContactsContract.Contacts.DISPLAY_NAME,
                    ContactsContract.Contacts.HAS_PHONE_NUMBER,
                    ContactsContract.Contacts._ID
                )
                val cursor = uri?.let {
                    contentResolver.query(
                        it,
                        projection,
                        null,
                        null,
                        null
                    )
                }
                cursor?.use {
                    if (it.count > 0) {
                        it.moveToFirst()
                        val name = it.getString(0)
                        val hasPhoneNumber = it.getString(1)
                        val contactId = it.getString(2)
                        if (hasPhoneNumber.toInt() > 0) {
                            getPhoneNumber(contactId)?.let { phoneNumber ->
                                showDialog("$name\n$phoneNumber")
                            }
                        }
                    }

                    it.close()
                }
            }

        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    pickContactLauncher.launch(null)
                }
            }
    }

    private fun showDialog(msg: String) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(msg).setPositiveButton(R.string.ok) { _, _ -> dialog.cancel() }
            .setCancelable(false)
        dialog = builder.create()
        dialog.show()
    }

    @SuppressLint("Range", "Recycle")
    private fun getPhoneNumber(contactId: String): String? {
        val cr = contentResolver
        val phones = cr.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
            null,
            null
        )
        phones?.apply {
            use {
                if (it.moveToNext()) {
                    return phones.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                }
            }
        }
        return null
    }

    private fun canReadContact(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED
    }

}