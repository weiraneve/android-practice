package com.thoughtworks.android.ui.navigation

import android.os.Bundle
import com.thoughtworks.android.PracticeApp
import com.thoughtworks.android.R
import com.thoughtworks.android.common.BaseActivity
import com.thoughtworks.android.ui.navigation.composenav.ComposeNavActivity
import com.thoughtworks.android.ui.navigation.fragmentnav.FragmentNavActivity

class NavigationActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        PracticeApp.addActivity(this)
        super.onCreate(savedInstanceState)
        setContentView()
        generateButtonAndUI(R.string.fragment_navigation, FragmentNavActivity::class.java)
        generateButtonAndUI(R.string.compose_navigation, ComposeNavActivity::class.java)
    }
}