package com.thoughtworks.android

import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.thoughtworks.android.ui.LoginActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class LoginActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule<LoginActivity>(
        Intent(
            ApplicationProvider.getApplicationContext(),
            LoginActivity::class.java
        )
    )

    @Test
    fun test_login_page() {
        onView(withId(R.id.login)).perform(click())
        onView(withId(R.id.logo)).check(matches(isDisplayed()))
        onView(withId(R.id.checkbox_remember_me)).perform(click()).check(matches(isChecked()))
    }
}