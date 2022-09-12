package com.thoughtworks.android.ui.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.thoughtworks.android.R

class RecyclerViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recycler_view_layout)
    }
}