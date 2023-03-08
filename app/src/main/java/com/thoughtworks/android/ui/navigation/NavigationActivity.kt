package com.thoughtworks.android.ui.navigation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.thoughtworks.android.PracticeApp
import com.thoughtworks.android.R

class NavigationActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        PracticeApp.addActivity(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_nav_container) as NavHostFragment
        navController = navHostFragment.navController

        // Setup the bottom navigation view with navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigationView.setupWithNavController(navController)

        // Setup the ActionBar with navController and 3 top level destinations
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.titleScreen, R.id.leaderboard, R.id.register)
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        setOnBackPressedCallback()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val options = navOptions { launchSingleTop = true }
        return when (item.itemId) {
            R.id.settings -> {
                navController.navigate(R.id.settings, null, options)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setOnBackPressedCallback() {
        onBackPressedDispatcher.addCallback(onBackPressedCallback)
//        navController.addOnDestinationChangedListener { navigationController, _, _ ->
//            onBackPressedCallback.isEnabled =
//                navigationController.backQueue.count { it.destination !is NavGraph } > 1
//        }
    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            val tabList = listOf(R.id.titleScreen, R.id.leaderboard, R.id.form)
            if (tabList.contains(navController.currentDestination?.id)) {
                handleExit()
            } else navController.popBackStack()
        }
    }

    private fun handleExit() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            Toast.makeText(
                applicationContext, AGAIN_BACK,
                Toast.LENGTH_SHORT
            ).show()
            exitTime = System.currentTimeMillis()
        } else PracticeApp.exit()
    }

    companion object {
        var exitTime: Long = 0
        const val AGAIN_BACK = "再按一次退出程序"
    }
}