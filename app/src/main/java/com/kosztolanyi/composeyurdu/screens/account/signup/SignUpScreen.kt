package com.kosztolanyi.composeyurdu.screens.account.signup

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.kosztolanyi.composeyurdu.ui.theme.LightGray

@Composable
fun SignUpScreen(viewModel : SignUpViewModel, navHostController: NavHostController) {
    val systemUiController = rememberSystemUiController()

    LaunchedEffect(key1 = true){
        systemUiController.setSystemBarsColor(
            color = LightGray
        )
    }

    Scaffold(topBar = {
        SignUpTopAppBar(navHostController = navHostController)
    }) {
        SignUpContent(viewModel,navHostController)
    }
}


