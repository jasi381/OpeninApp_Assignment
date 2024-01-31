package com.jasmeet.openinapp.screens

sealed class Screens(val route: String) {

    data object LinksScreen : Screens("linksScreen")
    data object CoursesScreen : Screens("coursesScreen")
    data object AddScreen : Screens("AddScreen")
    data object CampaignsScreen : Screens("campaignsScreen")
    data object ProfileScreen : Screens("profileScreen")
}