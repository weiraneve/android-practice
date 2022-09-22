package com.thoughtworks.android

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.thoughtworks.android.ui.ConstraintActivity
import com.thoughtworks.android.ui.LoginActivity
import com.thoughtworks.android.ui.thread.RxJavaActivity
import com.thoughtworks.android.ui.fragment.MyFragmentActivity
import com.thoughtworks.android.ui.recyclerview.TweetsActivity
import com.thoughtworks.android.ui.thread.HandlerActivity
import com.thoughtworks.android.ui.thread.ThreadActivity

class MainActivity : AppCompatActivity() {

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
    }

    private fun initContactUI() {
        val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI).apply {
            type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
        }
        resultLauncher.launch(intent)
    }

    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val uri: Uri? = data?.data
                val contact = getPhoneContacts(uri)
                if (contact != null) {
                    val name = contact[0]
                    val number = contact[1]
                    showDialog("$name\n$number")
                }
            }
        }

    @SuppressLint("Range")
    private fun getPhoneContacts(uri: Uri?): Array<String?>? {
        val contact = arrayOfNulls<String>(2)
        val contentResolver = contentResolver
        val projection: Array<String> = arrayOf(
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )
        val cursor: Cursor? = uri?.let { contentResolver.query(it, projection, null, null, null) }
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

    private fun canReadContact(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_CONTACTS
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun generateButtons() {
        generateButtonAndUI(R.string.constraint_layout, ConstraintActivity::class.java)
        generateButtonAndUI(R.string.login, LoginActivity::class.java)
        generatePickButtonAndUI()
        generateButtonAndUI(R.string.fragment, MyFragmentActivity::class.java)
        generateButtonAndUI(R.string.recycler_view, TweetsActivity::class.java)
        generateButtonAndUI(R.string.thread, ThreadActivity::class.java)
        generateButtonAndUI(R.string.handler, HandlerActivity::class.java)
        generateButtonAndUI(R.string.rxjava, RxJavaActivity::class.java)

        addButton(getString(R.string.button_9))
        addButton(getString(R.string.button_10))
    }

    private fun <T: AppCompatActivity> generateButtonAndUI(layoutInt: Int, java: Class<T>) {
        addButton(getString(layoutInt)) {
            startActivity(Intent(this, java))
        }
    }

    private fun generatePickButtonAndUI() {
        addButton(getString(R.string.pick_contact)) {
            if (canReadContact()) {
                initContactUI()
            } else {
                permissionLauncher.launch(Manifest.permission.READ_CONTACTS)
            }
        }
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
}