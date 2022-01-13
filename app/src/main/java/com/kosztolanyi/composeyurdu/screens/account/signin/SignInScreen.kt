package com.kosztolanyi.composeyurdu.screens.account.signin

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.kosztolanyi.composeyurdu.screens.account.signup.SignUpViewModel
import com.kosztolanyi.composeyurdu.ui.theme.LightGray

@ExperimentalComposeUiApi
@Composable
fun SignInScreen(viewModel: SignInViewModel, navHostController: NavHostController) {
    val systemUiController = rememberSystemUiController()

    LaunchedEffect(key1 = true){
        systemUiController.setSystemBarsColor(
            color = LightGray
        )
    }

    Scaffold(
        topBar = {
            SignInTopAppBar{
                //Todo
                navHostController.popBackStack()
            }
        }
    ) {
        SignInContent(viewModel = viewModel, navHostController = navHostController)
    }
}




