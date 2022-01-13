package com.kosztolanyi.composeyurdu.screens.campaign

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import com.kosztolanyi.composeyurdu.components.appbars.MainTopAppBar
import com.kosztolanyi.composeyurdu.data.HomeViewModel

@Composable
fun CampaignScreen(viewModel: HomeViewModel) {
    Scaffold(
        topBar = {
            MainTopAppBar(topBarTitle = "Kampanyalar")
        }
    ) {
        CampaignContent(campaignList = viewModel.homeDataItem.campaigns)
    }
}

