package com.jasmeet.openinapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.jasmeet.openinapp.appComponents.BottomBar
import com.jasmeet.openinapp.navigation.MainNavigationGraph
import com.jasmeet.openinapp.tokenManager.TokenManager
import com.jasmeet.openinapp.ui.theme.OpeninAppTheme
import com.jasmeet.openinapp.utils.BottomNavigationItem
import com.jasmeet.openinapp.viewModel.MainViewModel

/**
 * @author: Jasmeet Singh
 */
class MainActivity : ComponentActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tokenManager = TokenManager(this)

        val savedToken = tokenManager.getToken()

        if (savedToken.isNullOrEmpty()) {
            /**
             * If the token is not saved, obtain it and save it
             * For example, you might get the token during the login process
             */

            val obtainedToken = this.getString(R.string.tokken)
            tokenManager.saveToken(obtainedToken)
        }

        viewModel = MainViewModel(tokenManager)
        enableEdgeToEdge()

        setContent {
            val navHost = rememberNavController()
            OpeninAppTheme {
                MainApp(viewModel = viewModel)
            }
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun MainApp(viewModel: MainViewModel) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val itemList = listOf(
        BottomNavigationItem.Links,
        BottomNavigationItem.Courses,
        BottomNavigationItem.Add,
        BottomNavigationItem.Campaigns,
        BottomNavigationItem.Profile
    )

    val bottomBarDestination = itemList.any {
        it.route == currentDestination?.route
    }

    Scaffold(
        bottomBar = {
            if (bottomBarDestination) {
                BottomBar(
                    navController = navController,
                    currentDestination = currentDestination,
                    screens = itemList
                )
            }
        },
    ) {
        MainNavigationGraph(navHostController = navController, viewModel)
    }
}