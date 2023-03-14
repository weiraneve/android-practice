package com.thoughtworks.android.ui.graphql

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.thoughtworks.android.ui.graphql.ui.GraphqlHome

class GraphqlActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GraphqlHome()
        }
    }
}