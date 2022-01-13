package com.kosztolanyi.composeyurdu.components.bottombar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route : String,
    val title : String,
    val icon : ImageVector
){
    //BOTTOM NAVIGATION
    object Home : BottomBarScreen(
        route = "home",
        title = "Anasayfa",
        icon = Icons.Rounded.Home,
    )
    object Campaigns : BottomBarScreen(
        route = "campaigns",
        title = "Kampanya",
        icon = Icons.Rounded.Campaign,
    )
    object Categories : BottomBarScreen(
        route = "categories",
        title = "Kategoriler",
        icon = Icons.Rounded.Category,
    )
    object Search : BottomBarScreen(
        route = "search",
        title = "Arama",
        icon = Icons.Rounded.Search,
    )
    object Cart : BottomBarScreen(
        route = "cart",
        title = "Sepetim",
        icon = Icons.Rounded.ShoppingCart,
    )


}
