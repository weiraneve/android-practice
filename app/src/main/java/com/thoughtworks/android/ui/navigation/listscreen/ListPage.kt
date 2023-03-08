package com.thoughtworks.android.ui.navigation.listscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.thoughtworks.android.ui.navigation.BaseNavFragment

class ListPage : Fragment(), BaseNavFragment {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                Text("HomeFragment")
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    (0..10).forEach {
                        item(it) {
                            Spacer(modifier = Modifier.height(100.dp))
                            Text(text = it.toString())
                        }
                    }
                }
            }
        }
    }

    override fun onBackPressed(): Boolean = false
}