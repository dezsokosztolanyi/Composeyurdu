package com.kosztolanyi.composeyurdu.screens.detail

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.kosztolanyi.composeyurdu.components.appbars.OtherAppBar
import com.kosztolanyi.composeyurdu.data.HomeViewModel

@ExperimentalPagerApi
@Composable
fun DetailScreen(viewModel: HomeViewModel, navHostController: NavHostController, bookId: String) {
    Scaffold(topBar = {
        OtherAppBar(title = "Ürün Detay") {
            navHostController.navigateUp()
        }
    }) {
        DetailContent(viewModel,navHostController,bookId)
    }

}


