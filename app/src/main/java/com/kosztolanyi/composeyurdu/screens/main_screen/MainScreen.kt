package com.kosztolanyi.composeyurdu.screens.main_screen

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.firebase.firestore.FirebaseFirestore
import com.kosztolanyi.composeyurdu.components.bottombar.BottomBar
import com.kosztolanyi.composeyurdu.data.HomeViewModel
import com.kosztolanyi.composeyurdu.navigation.NavGraph
@ExperimentalPermissionsApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@ExperimentalPagerApi
@Composable
fun MainScreen(viewModel: HomeViewModel) {
    val navController = rememberNavController()

    Scaffold(bottomBar = {
        BottomBar(navHostController = navController)
    }) {
        NavGraph(navHostController = navController,viewModel = viewModel)
    }
}

