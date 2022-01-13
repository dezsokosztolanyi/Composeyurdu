package com.kosztolanyi.composeyurdu.screens.seeall

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
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
import com.kosztolanyi.composeyurdu.components.ratebar.RateBar
import com.kosztolanyi.composeyurdu.data.HomeViewModel
import com.kosztolanyi.composeyurdu.models.mybook.BookDetail
import com.kosztolanyi.composeyurdu.navigation.Destinations
import com.kosztolanyi.composeyurdu.ui.theme.DarkGray
import com.kosztolanyi.composeyurdu.ui.theme.kitapyurduGray
import com.kosztolanyi.composeyurdu.ui.theme.kitapyurduOrange
import com.kosztolanyi.composeyurdu.util.Resource

@Composable
fun SeeAllContent(navHostController: NavHostController, homeViewModel: HomeViewModel) {
    val seeAllItems = homeViewModel.seeAllItems.collectAsState()
//    println("see all content ${homeViewModel.seeAllItems.value.data.toString()}")
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        when (seeAllItems.value) {
            is Resource.Loading -> {
                CircularProgressIndicator(color = kitapyurduOrange)
            }
            is Resource.Success -> {
                LazyColumn(modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 56.dp)) {
                    if (!seeAllItems.value.data.isNullOrEmpty()){
                        items(seeAllItems.value.data!!) { item ->
                            SeeAllItem(item = item, onItemClicked = {
                                navHostController.navigate(Destinations.Detail.route + "/$it")
                            })
                        }
                    }
                }
            }
            is Resource.Error -> {
                Text(text = "An Error Occurred")
            }

        }
    }
}

@Composable
fun SeeAllItem(item: BookDetail, onItemClicked: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
            .padding(vertical = 2.dp)
            .clickable {
                onItemClicked(item.bookId)
            }
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
        ) {
            Image(
                modifier = Modifier
                    .weight(2.1f)
                    .fillMaxSize()
                    .padding(vertical = 6.dp),
                painter = rememberImagePainter(data = item.bookImageUrl) {
                    scale(Scale.FILL)
                },
                contentDescription = "Book Image"
            )
            Column(
                modifier = Modifier
                    .weight(3.5f)
                    .fillMaxHeight(), verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = item.bookName,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = item.bookPublisher,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Light
                )
                Text(
                    text = item.bookAuthor,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Light
                )
                RateBar(rate = item.rate, arrangement = Arrangement.Start)
            }
            Column(
                modifier = Modifier
                    .weight(1.5f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.End
            ) {
                Icon(
                    modifier = Modifier
                        .size(16.dp)
                        .weight(1f),
                    imageVector = Icons.Default.More,
                    contentDescription = "More",
                    tint = DarkGray
                )
                Column(
                    modifier = Modifier.weight(5f),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = item.price.currentPrice + " TL",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.LineThrough
                    )
                    Text(
                        text = item.price.currentPrice + " TL",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 2.dp), color = kitapyurduGray
        )
    }
}
