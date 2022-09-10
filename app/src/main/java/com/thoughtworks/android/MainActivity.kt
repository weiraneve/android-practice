package com.thoughtworks.android

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val buttonContainer: LinearLayout by lazy { findViewById(R.id.button_container) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        generateButtons()
    }

    private fun generateButtons() {
        addButton(getString(R.string.button1))
        addButton(getString(R.string.button2))
        addButton(getString(R.string.button3))
        addButton(getString(R.string.button4))
        addButton(getString(R.string.button5))
        addButton(getString(R.string.button6))
        addButton(getString(R.string.button7))
        addButton(getString(R.string.button8))
        addButton(getString(R.string.button9))
        addButton(getString(R.string.button10))

    }

    private fun addButton(name: String, onClickListener: View.OnClickListener? = null) {
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(0, resources.getDimensionPixelSize(R.dimen.margin_24), 0, 0)

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