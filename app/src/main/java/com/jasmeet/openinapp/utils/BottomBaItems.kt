package com.jasmeet.openinapp.utils

import com.jasmeet.openinapp.R
import com.jasmeet.openinapp.screens.Screens

sealed class BottomNavigationItem(
    val route: String,
    val selectedIcon: Int,
    val title: String
) {
    data object Links : BottomNavigationItem(
        route = Screens.LinksScreen.route,
        selectedIcon = R.drawable.ic_links,
        title = "Links"
    )

    data object Courses : BottomNavigationItem(
        route = Screens.CoursesScreen.route,
        selectedIcon = R.drawable.ic_courses,
        title = "Courses"
    )

    data object Add : BottomNavigationItem(
        route = Screens.AddScreen.route,
        selectedIcon = R.drawable.ic_add,
        title = ""
    )

    data object Campaigns : BottomNavigationItem(
        route = Screens.CampaignsScreen.route,
        selectedIcon = R.drawable.ic_campaigns,
        title = "Campaigns"
    )

    data object Profile : BottomNavigationItem(
        route = Screens.ProfileScreen.route,
        selectedIcon = R.drawable.ic_profile,
        title = "Profile"
    )
}