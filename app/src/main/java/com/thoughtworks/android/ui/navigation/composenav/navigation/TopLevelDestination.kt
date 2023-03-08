package com.thoughtworks.android.ui.navigation.composenav.navigation

import com.thoughtworks.android.R

enum class TopLevelDestination(
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val iconTextId: Int
) {
    FOR_YOU(
        selectedIcon = R.drawable.ic_upcoming,
        unselectedIcon = R.drawable.ic_upcoming_border,
        iconTextId = R.string.for_page
    ),
    SAVED(
        selectedIcon = R.drawable.ic_bookmarks,
        unselectedIcon = R.drawable.ic_bookmarks_border,
        iconTextId = R.string.saved
    ),
    INTEREST(
        selectedIcon = R.drawable.ic_menu_book,
        unselectedIcon = R.drawable.ic_menu_book_border,
        iconTextId = R.string.interests
    )
}