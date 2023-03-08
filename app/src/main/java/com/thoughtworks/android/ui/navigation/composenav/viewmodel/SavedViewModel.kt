package com.thoughtworks.android.ui.navigation.composenav.viewmodel

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.thoughtworks.android.ui.navigation.composenav.navigation.savedAnyRoute

sealed class SavedAction {
    data class UpdateUI(val navController: NavController) : SavedAction()
}

class SavedViewModel : ViewModel() {

    private fun updateComposeUI(navController: NavController) {
        navController.navigate(savedAnyRoute)
    }

    fun dispatch(action: SavedAction) {
        when (action) {
            is SavedAction.UpdateUI -> updateComposeUI(action.navController)
        }
    }
}