package com.kosztolanyi.composeyurdu.screens.detail.tab

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.*
import com.kosztolanyi.composeyurdu.models.mybook.BookDetail
import com.kosztolanyi.composeyurdu.ui.theme.kitapyurduOrange
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
fun TabScreen(bookDetail: BookDetail) {
    val pagerState = rememberPagerState(pageCount = 3)
    Column(modifier = Modifier.background(Color.White)) {
        TabsContent(bookDetail,pagerState)
    }
}

@ExperimentalPagerApi
@Composable
fun TabsContent(bookDetail: BookDetail, pagerState: PagerState) {

    var list = listOf("Künye", "Genel Bakış", "Yorumlar")
    var scope = rememberCoroutineScope()
    var pagerSize : Dp by remember {
        mutableStateOf(250.dp)
    }


    TabRow(
        modifier = Modifier
            .height(50.dp)
            .padding(vertical = 8.dp, horizontal = 8.dp)
            .clip(RoundedCornerShape(10))
            .border(1.dp, kitapyurduOrange),
        selectedTabIndex = pagerState.currentPage.also {
            LaunchedEffect(key1 = pagerState, block = {
                when(it){
                    0 -> {pagerSize = 250.dp}
                    1 -> {pagerSize = Dp.Infinity }
                    2 -> {pagerSize = 540.dp}
                }
            })

        },
        backgroundColor = Color.Transparent,
        contentColor = Color.White,
        indicator = { tabPosition ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPosition),
                height = 1.dp,
                color = kitapyurduOrange
            )
        }
    ) {
        list.forEachIndexed { index, _ ->
            Tab(modifier = Modifier
                .background(
                    if (pagerState.currentPage == index)
                        kitapyurduOrange
                    else
                        Color.White
                )
                .border(0.5.dp, kitapyurduOrange),
                text = {
                    Text(
                        text = list[index],
                        color =
                        if (pagerState.currentPage == index)
                            Color.White
                        else
                            kitapyurduOrange,
                        style = TextStyle(
                            fontSize = 14.sp
                        )
                    )
                },
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
            )
        }
    }


    HorizontalPager(
        modifier = Modifier.height(pagerSize),
        state = pagerState,
        dragEnabled = false) { page ->
        println(page.toString())
        when (page) {
            0 -> {
                println("Current: ${pagerState.currentPage}" )
                Tags(bookDetail = bookDetail){

                    println("Dp: ${it}" )
                }
            }
            1 -> {
                println("Current: ${pagerState.currentPage}" )
                OverLook{
                    println("Dp: ${it}" )
                }
            }
            2 -> {
                println("Current: ${pagerState.currentPage}" )
                AllComments()
            }
        }

    }
}



