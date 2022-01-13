package com.kosztolanyi.composeyurdu.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ShoppingCart
import com.kosztolanyi.composeyurdu.components.bottombar.BottomBarScreen

sealed class Destinations(val route : String){

    //SIGN IN & SIGN UP
    object SignIn : Destinations(
        route = "signIn",
    )
    object SignUp : Destinations(
        route = "signUp"
    )
    object Detail: Destinations(
        route = "detail"
    )
    object Account: Destinations(
        route = "account"
    )
    object Payment: Destinations(
        route = "payment"
    )
    object Barcode: Destinations(
        route = "barcode"
    )
    object SeeAll: Destinations(
        route = "seeall"
    )
}