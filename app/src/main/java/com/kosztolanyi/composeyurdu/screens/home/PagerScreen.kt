package com.kosztolanyi.composeyurdu.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.kosztolanyi.composeyurdu.models.Banner
import com.kosztolanyi.composeyurdu.ui.theme.Purple700
import com.kosztolanyi.composeyurdu.ui.theme.kitapyurduOrange

@ExperimentalPagerApi
@Composable
fun PagerScreen(bannerList : List<Banner>) {
    val pagerState = rememberPagerState(pageCount = bannerList.size)

    Column(modifier = Modifier
        .height(240.dp)
        .fillMaxWidth()) {

        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            state = pagerState
        ) { page ->
            PageUI(banner = bannerList[page])
        }
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 20.dp, vertical = 4.dp),
            activeColor = kitapyurduOrange
        )
    }
}

@Composable
fun PageUI(banner : Banner) {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = rememberImagePainter(
                data = banner.bannerImageUrl,
                builder = {
                    scale(Scale.FILL)
                }), contentDescription = "Banner")
    }
}