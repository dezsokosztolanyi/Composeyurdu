package com.kosztolanyi.composeyurdu.screens.cart

import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.kosztolanyi.composeyurdu.components.appbars.MainTopAppBar

@Composable
fun CartScreen(viewModel: CartViewModel, navHostController: NavHostController) {


    Scaffold(
        topBar = {
            MainTopAppBar("Sepetim")
        }) {
        CartScreenContent(viewModel,navHostController)
    }
}







