package com.thoughtworks.android.ui.navigation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.thoughtworks.android.PracticeApp
import com.thoughtworks.android.R
import com.thoughtworks.android.ui.navigation.composenav.ComposeNavActivity
import com.thoughtworks.android.ui.navigation.fragmentnav.FragmentNavActivity

class NavigationActivity : AppCompatActivity() {

    private val buttonContainer: LinearLayout by lazy { findViewById(R.id.nav_button_container) }

    override fun onCreate(savedInstanceState: Bundle?) {
        PracticeApp.addActivity(this)
        super.onCreate(savedInstanceState)
        startFragmentNavigationUi()
    }

    private fun startFragmentNavigationUi() {
        setContentView(R.layout.activity_navigation)
        generateButtonAndUI(R.string.fragment_navigation, FragmentNavActivity::class.java)
        generateButtonAndUI(R.string.compose_navigation, ComposeNavActivity::class.java)
    }

    private fun <T : AppCompatActivity> generateButtonAndUI(layoutInt: Int, java: Class<T>) {
        addButton(getString(layoutInt)) {
            startActivity(Intent(this, java))
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