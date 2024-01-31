package com.jasmeet.openinapp.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jasmeet.openinapp.screens.LinkScreen
import com.jasmeet.openinapp.screens.Screens
import com.jasmeet.openinapp.viewModel.MainViewModel

@Composable
fun MainNavigationGraph(
    navHostController: NavHostController,
    viewModel: MainViewModel,
) {


    NavHost(
        navController = navHostController,
        route = GRAPH.MAIN_GRAPH,
        startDestination = Screens.LinksScreen.route
    ) {
        composable(
            route = Screens.LinksScreen.route,
        ) {
            LinkScreen(viewModel, navHostController)
        }
        composable(
            route = Screens.CoursesScreen.route,

            ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .background(Color.DarkGray)
            ) {

            }
        }
        composable(
            route = Screens.AddScreen.route,
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .background(Color.DarkGray)
            ) {

            }

        }


        composable(Screens.CampaignsScreen.route) {
            Column(
                Modifier
                    .fillMaxSize()
                    .background(Color.Green)
            ) {

            }
        }
        composable(Screens.ProfileScreen.route) {
            Column(
                Modifier
                    .fillMaxSize()
                    .background(Color.DarkGray)
            ) {

            }
        }
    }

}

object GRAPH {
    const val MAIN_GRAPH = "main graph"
}