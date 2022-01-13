package com.kosztolanyi.composeyurdu.screens.home

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.kosztolanyi.composeyurdu.components.appbars.HomeTopAppBar
import com.kosztolanyi.composeyurdu.components.appbars.MainTopAppBar
import com.kosztolanyi.composeyurdu.data.HomeViewModel
import com.kosztolanyi.composeyurdu.navigation.Destinations

@ExperimentalPagerApi
@Composable
fun HomeScreen(viewModel: HomeViewModel, navHostController: NavHostController) {
    Scaffold(topBar = {
        HomeTopAppBar(isUserSignedIn = true){
            navHostController.navigate(Destinations.Account.route)
        }
    }) {
        HomeContent(
            viewModel = viewModel,
            navHostController = navHostController
        )
    }
}

