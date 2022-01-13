package com.kosztolanyi.composeyurdu.components.bottombar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.kosztolanyi.composeyurdu.ui.theme.*

@Composable
fun BottomBar(navHostController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Campaigns,
        BottomBarScreen.Categories,
        BottomBarScreen.Search,
        BottomBarScreen.Cart
    )

    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation(backgroundColor = kitapyurduBottomBarColor) {
        screens.forEach { screen ->
            AddBottomBarItem(
                screen = screen,
                currentDestination = currentDestination,
                navHostController = navHostController
            )
        }
    }
}

@Composable
fun RowScope.AddBottomBarItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navHostController: NavHostController
) {
    BottomNavigationItem(
        label = {
        Text(modifier = Modifier.fillMaxWidth(), text = screen.title, fontSize = 8.sp, maxLines = 1, softWrap = true)
    }, icon = {
        Icon(imageVector = screen.icon, contentDescription = screen.title)
    },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        onClick = {
            navHostController.navigate(screen.route){
                popUpTo(navHostController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },
        selectedContentColor = kitapyurduOrange,
        unselectedContentColor = Color.DarkGray
    )
}





