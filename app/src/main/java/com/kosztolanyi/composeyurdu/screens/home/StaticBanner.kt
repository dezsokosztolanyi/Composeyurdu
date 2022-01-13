package com.kosztolanyi.composeyurdu.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.kosztolanyi.composeyurdu.models.Banner
import com.kosztolanyi.composeyurdu.models.StaticBanner

@Composable
fun StaticBanner(banner: StaticBanner) {
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .fillMaxWidth()
            .height(70.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(modifier = Modifier.fillMaxSize(), painter = rememberImagePainter(data = banner.bannerImageUrl, builder = {
            crossfade(true)
            scale(Scale.FILL)
        }), contentDescription = "")
    }
}