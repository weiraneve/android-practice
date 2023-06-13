package com.thoughtworks.android

import android.os.Bundle
import com.thoughtworks.android.common.activity.BaseActivity
import com.thoughtworks.android.ui.someactivity.ConstraintActivity
import com.thoughtworks.android.ui.someactivity.LoginActivity
import com.thoughtworks.android.ui.someactivity.SharedPreferenceActivity
import com.thoughtworks.android.ui.compose.ComposeActivity
import com.thoughtworks.android.ui.someactivity.ContactActivity
import com.thoughtworks.android.ui.feed.FeedActivity
import com.thoughtworks.android.ui.fragment.MyFragmentActivity
import com.thoughtworks.android.ui.graphql.GraphqlActivity
import com.thoughtworks.android.ui.jsbridge.JsBridgeActivity
import com.thoughtworks.android.ui.navigation.NavigationActivity
import com.thoughtworks.android.ui.openother.OpenOtherActivity
import com.thoughtworks.android.ui.recyclerview.TweetsActivity
import com.thoughtworks.android.ui.thread.HandlerActivity
import com.thoughtworks.android.ui.thread.RxJavaActivity
import com.thoughtworks.android.ui.thread.ThreadActivity
import com.thoughtworks.android.ui.web.WebActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        PracticeApp.addActivity(this)
        super.onCreate(savedInstanceState)
        setContentView()
        generateButtons()
    }

    private fun generateButtons() {
        generateButtonAndUI(R.string.constraint_layout, ConstraintActivity::class.java)
        generateButtonAndUI(R.string.login, LoginActivity::class.java)
        generateButtonAndUI(R.string.pick_contact, ContactActivity::class.java)
        generateButtonAndUI(R.string.fragment, MyFragmentActivity::class.java)
        generateButtonAndUI(R.string.recycler_view, TweetsActivity::class.java)
        generateButtonAndUI(R.string.thread, ThreadActivity::class.java)
        generateButtonAndUI(R.string.handler, HandlerActivity::class.java)
        generateButtonAndUI(R.string.rxjava, RxJavaActivity::class.java)
        generateButtonAndUI(R.string.sp, SharedPreferenceActivity::class.java)
        generateButtonAndUI(R.string.compose, ComposeActivity::class.java)
        generateButtonAndUI(R.string.feed, FeedActivity::class.java)
        generateButtonAndUI(R.string.navigation, NavigationActivity::class.java)
        generateButtonAndUI(R.string.jsbridge, JsBridgeActivity::class.java)
        generateButtonAndUI(R.string.web, WebActivity::class.java)
        generateButtonAndUI(R.string.graphql, GraphqlActivity::class.java)
        generateButtonAndUI(R.string.open_other_app, OpenOtherActivity::class.java)
    }

}