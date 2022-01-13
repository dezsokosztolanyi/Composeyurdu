package com.kosztolanyi.composeyurdu.screens.account.account

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.kosztolanyi.composeyurdu.components.appbars.MainTopAppBar

@Composable
fun AccountScreen(navHostController: NavHostController, viewModel: AccountViewModel) {
    Scaffold(topBar = {
        MainTopAppBar(topBarTitle = "Hesabım")
    }) {
        AccountContent(viewModel = viewModel,navHostController = navHostController)
    }
}