package com.thoughtworks.android.ui.navigation.composenav.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.thoughtworks.android.ui.navigation.composenav.viewmodel.SavedAction
import com.thoughtworks.android.ui.navigation.composenav.viewmodel.SavedViewModel

@Composable
fun SavedScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(100.dp))
        val viewModel: SavedViewModel = viewModel()
        Button(onClick = { viewModel::dispatch.invoke(SavedAction.UpdateUI(navController)) }) {
            Text("跳转 saveAny界面")
        }
//        Button(onClick = { navController.navigate(savedAnyRoute) }) { Text("跳转 saveAny界面") }
    }
}