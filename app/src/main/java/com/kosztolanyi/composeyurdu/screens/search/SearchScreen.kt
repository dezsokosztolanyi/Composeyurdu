package com.kosztolanyi.composeyurdu.screens.search

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import com.kosztolanyi.composeyurdu.components.appbars.MainTopAppBar

@ExperimentalComposeUiApi
@Composable
fun SearchScreen(viewModel: SearchViewModel,navHostController: NavHostController) {
    val text = viewModel.searchQuery.component1()
    Scaffold(
        topBar = {
            MainTopAppBar(topBarTitle = "Ara")
        }
    ) {
        SearchScreenContent(text,viewModel,navHostController)
    }
}

