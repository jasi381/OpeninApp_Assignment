package com.jasmeet.openinapp.appComponents

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import com.jasmeet.openinapp.R
import com.jasmeet.openinapp.screens.Screens
import com.jasmeet.openinapp.ui.theme.figtree
import com.jasmeet.openinapp.utils.BottomNavigationItem

@Composable
fun BottomBar(
    navController: NavHostController,
    currentDestination: NavDestination?,
    screens: List<BottomNavigationItem>
) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 0.dp,
    ) {
        screens.forEach { screen ->
            if (screen == BottomNavigationItem.Add) {
                AddFloatingActionButton(navController = navController)
            } else {
                AddBottomNavItem(
                    navHostController = navController,
                    screen = screen,
                    currentDestination = currentDestination
                )
            }
        }
    }
}

@Composable
fun AddFloatingActionButton(navController: NavHostController) {

    Surface(
        shadowElevation = 8.dp,
        onClick = { navController.navigate(Screens.AddScreen.route) },
        shape = CircleShape,
        color = Color(0xff0e6fff),
        modifier = Modifier.padding(bottom = 40.dp, top = 5.dp)

    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_add),
            contentDescription = "",
            tint = Color(0xffffffff),
            modifier = Modifier
                .padding(10.dp)
                .size(28.dp)
        )
    }

}

@Composable
fun RowScope.AddBottomNavItem(
    navHostController: NavHostController,
    currentDestination: NavDestination?,
    screen: BottomNavigationItem,
) {
    NavigationBarItem(
        colors = NavigationBarItemColors(
            selectedIndicatorColor = Color.Transparent,
            disabledIconColor = Color.Transparent,
            disabledTextColor = Color.Transparent,
            unselectedIconColor = Color.Transparent,
            selectedIconColor = Color.Transparent,
            selectedTextColor = Color.Transparent,
            unselectedTextColor = Color.Transparent,
        ),
        selected = currentDestination?.route == screen.route,
        onClick = {
            if (currentDestination?.route != screen.route) {
                navHostController.navigate(screen.route) {
                    popUpTo(navHostController.graph.startDestinationId)
                    launchSingleTop = true
                }
            }
        },
        icon = {
            Icon(
                imageVector = ImageVector.vectorResource(id = screen.selectedIcon),
                contentDescription = "Icon",
                tint = if (currentDestination?.route == screen.route) {
                    Color(0xFF000000)
                } else {
                    Color(0xFF999ca0)
                },
                modifier = Modifier.then(
                    if (currentDestination?.route == screen.route) {
                        Modifier.padding(bottom = 2.dp)
                    } else {
                        Modifier.padding(top = 0.dp)
                    }
                )
            )
        },
        label = {
            Text(
                text = screen.title,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = figtree,
                    fontWeight = FontWeight(500),
                    color = if (currentDestination?.route == screen.route) {
                        Color(0xff000000)
                    } else {
                        Color(0xFF999ca0)
                    },
                    textAlign = TextAlign.Center,
                ),
            )
        }
    )
}