package com.kosztolanyi.composeyurdu.components.appbars

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kosztolanyi.composeyurdu.R
import com.kosztolanyi.composeyurdu.ui.theme.BgColor
import com.kosztolanyi.composeyurdu.ui.theme.kitapyurduOrange

@Composable
fun MainTopAppBar(topBarTitle : String) {
    TopAppBar(backgroundColor = kitapyurduOrange, contentPadding = PaddingValues(0.dp), elevation = 0.dp) {
        Image(
            modifier = Modifier
                .fillMaxHeight()
                .width(40.dp),
            painter = painterResource(id = R.drawable.ky_icon),
            contentDescription = "Kitapyurdu İkon"
        )
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(end = 40.dp), contentAlignment = Center) {
            Text(
                text = topBarTitle,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}