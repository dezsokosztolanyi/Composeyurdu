package com.kosztolanyi.composeyurdu.screens.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.firebase.auth.FirebaseAuth
import com.kosztolanyi.composeyurdu.data.HomeViewModel
import com.kosztolanyi.composeyurdu.models.HomeMainItem
import com.kosztolanyi.composeyurdu.navigation.Destinations
import com.kosztolanyi.composeyurdu.ui.theme.kitapyurduBookBorder
import com.kosztolanyi.composeyurdu.ui.theme.kitapyurduGreen
import com.kosztolanyi.composeyurdu.ui.theme.kitapyurduLazyRowBackgroundColor
import com.kosztolanyi.composeyurdu.util.HomeItemType

@ExperimentalPagerApi
@Composable
fun HomeContent(viewModel: HomeViewModel, navHostController : NavHostController) {
    val scrollState = rememberScrollState()
    val homeScreenBanners by viewModel.get3RandomStaticBanners

    println(FirebaseAuth.getInstance().currentUser?.uid)

    //todo improve this launched effect
    LaunchedEffect(key1 = true, block = {
        viewModel.getDetailBanners()
    })

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(bottom = 60.dp)
    ) {
        if (!viewModel.homeDataItem.banner.isNullOrEmpty()) {
            PagerScreen(bannerList = viewModel.homeDataItem.banner)
//            PagerScreen(bannerList = viewModel.getagerImages())
            Spacer(modifier = Modifier.height(12.dp))
            StaticBanner(banner = homeScreenBanners.first())
            Spacer(modifier = Modifier.height(12.dp))
        }

        if (!viewModel.homeDataItem.bestseller.isNullOrEmpty()) {
            HomeBookItem(
                dataList = viewModel.convertBestsellerToHomeMainItem(),
                HomeItemType.BESTSELLER,
                onItemClicked = {
                    navHostController.navigate(Destinations.Detail.route+"/$it")
                },
                onListClicked =  {
                    navHostController.navigate(Destinations.SeeAll.route + "/${HomeItemType.BESTSELLER.argumentName}")
                })
        }
        if (!viewModel.homeDataItem.news.isNullOrEmpty()) {
            HomeBookItem(
                dataList = viewModel.convertNewsToHomeMainItem(),
                HomeItemType.NEW,
                onItemClicked = {
                    navHostController.navigate(Destinations.Detail.route+"/$it")
                },
                onListClicked =  {
                    navHostController.navigate(Destinations.SeeAll.route + "/${HomeItemType.NEW.argumentName}")
                })
        }
        if (!viewModel.homeDataItem.popular.isNullOrEmpty()) {
            HomeBookItem(
                dataList = viewModel.convertPopularToHomeMainItem(),
                HomeItemType.POPULAR,
                onItemClicked = {
                    navHostController.navigate(Destinations.Detail.route+"/$it")
                },
                onListClicked =  {
                    navHostController.navigate(Destinations.SeeAll.route + "/${HomeItemType.POPULAR.argumentName}")

                })
            StaticBanner(banner = homeScreenBanners[1])
        }
        if (!viewModel.homeDataItem.authors.isNullOrEmpty()) {
            HomeBookItem(
                dataList = viewModel.convertAuthorToHomeMainItem(),
                homeItemType = HomeItemType.AUTHOR,
                onItemClicked = {

                },
                onListClicked =  {

                })
        }
        if (!viewModel.homeDataItem.publishers.isNullOrEmpty()) {
            HomeBookItem(
                dataList = viewModel.convertPublishersToHomeMainItem(),
                homeItemType = HomeItemType.PUBLISHER,
                onItemClicked = {

                },
            onListClicked =  {

            })
            Spacer(modifier = Modifier.height(26.dp))
//            Box(modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 16.dp)){
//                ButtonWith2Icon(
//                    leadingIcon = Icons.Default.CardGiftcard,
//                    trailingIcon = Icons.Default.ArrowRight,
//                    navHostController = navHostController
//                )
//            }

        }

    }
}

@Composable
fun HomeBookItem(
    dataList: List<HomeMainItem>,
    homeItemType: HomeItemType,
    onItemClicked: (String) -> Unit,
    onListClicked: (HomeItemType) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(if (homeItemType == HomeItemType.PUBLISHER) 140.dp else 210.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 8.dp, top = 12.dp),
                text = homeItemType.topic,
                style = TextStyle(
                    color = kitapyurduGreen,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif
                )
            )

            Text(
                modifier = Modifier
                    .padding(end = 8.dp, top = 12.dp)
                    .align(Alignment.Bottom)
                    .clickable {
                        onListClicked(homeItemType)
                    },
                text = homeItemType.seeAll,
                style = TextStyle(
                    color = kitapyurduGreen,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace
                )
            )
        }

        Spacer(modifier = Modifier.height(4.dp))
        Row(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(
                    brush = Brush.verticalGradient(
                        listOf(
                            kitapyurduLazyRowBackgroundColor,
                            Color.White
                        )
                    )
                )
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.Bottom
        ) {

            LazyRow(contentPadding = PaddingValues(horizontal = 16.dp)) {
                items(dataList) { homeMainItem ->
                    if (homeItemType == HomeItemType.POPULAR || homeItemType == HomeItemType.NEW || homeItemType == HomeItemType.BESTSELLER) {
                        ScrollableBookItem(book = homeMainItem, homeItemType = homeItemType) { itemId ->
                            println(itemId)
                            onItemClicked(itemId)
                        }
                    } else {
                        ScrollableImageItem(image = homeMainItem, homeItemType = homeItemType) { itemId ->
                            println(itemId)
                            onItemClicked(itemId)
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun ScrollableBookItem(
    book: HomeMainItem,
    homeItemType: HomeItemType,
    onBookClicked: (String) -> Unit
) {
    Box(modifier = Modifier
        .height(150.dp)
        .width(105.dp)
        .padding(1.dp)
        .border(1.dp, kitapyurduBookBorder)
        .padding(horizontal = 4.dp)
        .clickable {
            onBookClicked(book.uid)
        }) {

        Image(
            painter = rememberImagePainter(
                data = book.url,
                builder = {
                    scale(Scale.FIT)
                    crossfade(true)
                }
            ),
            contentDescription = "",
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun ScrollableImageItem(
    image: HomeMainItem,
    homeItemType: HomeItemType,
    onImageClicked: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .height(if (homeItemType == HomeItemType.PUBLISHER) 160.dp else 160.dp)
            .width(105.dp)
            .padding(vertical = 4.dp)
            .border(
                width = 1.dp,
                color = if (homeItemType == HomeItemType.NEW || homeItemType == HomeItemType.BESTSELLER || homeItemType == HomeItemType.POPULAR) Color.DarkGray else Color.Transparent
            )
            .padding(horizontal = 4.dp)
            .clickable {
                onImageClicked(image.uid)
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = rememberImagePainter(
                data = image.url,
                builder = {
                    crossfade(true)
                }
            ),
            contentDescription = "",
            modifier = Modifier
//                .padding(top = 6.dp)
                .size(if (homeItemType == HomeItemType.PUBLISHER) 80.dp else 90.dp)
                .clip(CircleShape)
                .border(width = 1.dp, Color.LightGray, CircleShape)
        )
        when (homeItemType) {
            HomeItemType.AUTHOR -> {
                Text(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    text = image.name,
                    color = Color.DarkGray,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            HomeItemType.PUBLISHER -> {
                //No text
            }
            else -> {
                //No text
            }
        }
    }
}