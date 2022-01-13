package com.kosztolanyi.composeyurdu.screens.seeall

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.More
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.kosztolanyi.composeyurdu.components.appbars.OtherAppBar
import com.kosztolanyi.composeyurdu.components.bottombar.BottomBarScreen
import com.kosztolanyi.composeyurdu.components.ratebar.RateBar
import com.kosztolanyi.composeyurdu.data.HomeViewModel
import com.kosztolanyi.composeyurdu.models.mybook.BookDetail
import com.kosztolanyi.composeyurdu.navigation.Destinations
import com.kosztolanyi.composeyurdu.ui.theme.DarkGray
import com.kosztolanyi.composeyurdu.ui.theme.kitapyurduGray
import com.kosztolanyi.composeyurdu.util.HomeItemType
import com.kosztolanyi.composeyurdu.util.Resource

@Composable
fun SeeAllScreen(
    type: HomeItemType,
    navHostController: NavHostController,
    homeViewModel: HomeViewModel
) {

    Scaffold(topBar = {
        OtherAppBar(title = type.topic) {
            navHostController.navigateUp()
//            navHostController.navigate(BottomBarScreen.Home.route)
//            navHostController.popBackStack()
        }
    }) {
        SeeAllContent(navHostController, homeViewModel)
    }
}

