package com.kosztolanyi.composeyurdu.screens.detail

import android.widget.Toast
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material.icons.rounded.Timer
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.kosztolanyi.composeyurdu.R
import com.kosztolanyi.composeyurdu.components.alertdialog.CustomAlertDialog
import com.kosztolanyi.composeyurdu.components.ratebar.RateBar
import com.kosztolanyi.composeyurdu.models.Cart
import com.kosztolanyi.composeyurdu.data.HomeViewModel
import com.kosztolanyi.composeyurdu.models.mybook.BookDetail
import com.kosztolanyi.composeyurdu.models.mybook.Order
import com.kosztolanyi.composeyurdu.navigation.Destinations
import com.kosztolanyi.composeyurdu.screens.detail.tab.TabScreen
import com.kosztolanyi.composeyurdu.screens.home.StaticBanner
import com.kosztolanyi.composeyurdu.ui.theme.kitapyurduOrange
import com.kosztolanyi.composeyurdu.util.Resource
import java.util.*

@ExperimentalPagerApi
@Composable
fun DetailContent(
    viewModel: HomeViewModel,
    navHostController: NavHostController,
    bookId: String
) {
    val scrollState = rememberScrollState()
    val likeState = viewModel.state
    var isLoading by remember { mutableStateOf(true) }
    val bookDetailState by viewModel.selectedBook.collectAsState()
    val bookCartState by viewModel.selectedBookCartInfo.collectAsState()
    val detailScreenBanners by viewModel.get3RandomStaticBanners
    var dialogState by remember {
        mutableStateOf(false)
    }
    val favBook by viewModel.bookFav.collectAsState()
    val context = LocalContext.current


    LaunchedEffect(key1 = true, block = {
        viewModel.getDetailBanners()
    })

    LaunchedEffect(key1 = bookDetailState, block = {
        bookDetailState.data?.bookId?.let {
            viewModel.getSelectedBookCartInfo(it)
            if (!viewModel.isUserOnline().isNullOrEmpty()){
                viewModel.getFavBook()
            }
        }
    })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 60.dp), verticalArrangement = Arrangement.Center
    ) {
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = kitapyurduOrange)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(10f)
                .padding(horizontal = 8.dp)
                .padding(top = 10.dp)
                .verticalScroll(scrollState)
        ) {
            println("detail")
            if (dialogState){
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent), contentAlignment = Alignment.Center) {
                    CustomAlertDialog(
                        title = "Uyarı",
                        content = "Bu işlemi yapabilmeniz için oturum açmanız gerekir.",
                        positiveAnswer = "Giriş Yap / Üye Ol",
                        negativeAnswer = "İptal",
                        onPositiveAnswerClicked = {
                            dialogState = false
                            navHostController.navigate(Destinations.SignIn.route)
                        },
                        onNegativeAnswerClicked = {
                            dialogState = false
                        }, onDismissed = {
                            dialogState = false
                        })
                }
            }

            if (bookDetailState.data != null && bookDetailState.data?.bookId == bookId) {
                isLoading = false
                StaticBanner(banner = detailScreenBanners.first())
                BookSection(
                    bookDetail = bookDetailState.data!!,
                    likeState = likeState,
                    favBook = favBook,
                    onAddFavorite = {
                        if (favBook.data != null){
                            if (!viewModel.isUserOnline().isNullOrEmpty()){
                                viewModel.deleteFavBook(favBook.data!!)
                            }else{
                                dialogState = true
                            }
                        }else{
                            if (!viewModel.isUserOnline().isNullOrEmpty()){
                                viewModel.setFavBook()
                                viewModel.state = MutableTransitionState(Like.Initial)
                            }else{
                                dialogState = true
                            }
                        }
                    })
                TabScreen(bookDetail = bookDetailState.data!!)
                Footer(viewModel, viewModel.getBookDetail())
            }
        }
        //TODO CREATE A CUSTOM BUTTON
        when (bookCartState) {
            is Resource.Loading -> {
                println("bookCartState Loading")
            }
            is Resource.Success -> {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .background(kitapyurduOrange), contentAlignment = Alignment.BottomCenter
                ) {
                    if (bookCartState.data != null) {
                        //ürün sepette
                        Button(modifier = Modifier
                            .fillMaxSize(),
                            colors = ButtonDefaults.buttonColors(Color.Green), onClick = {
                                if (!viewModel.isUserOnline().isNullOrEmpty()) {
                                    val cart = Cart(
                                        cartId = bookCartState.data!!.cartId,
                                        userId = viewModel.isUserOnline().toString(),
                                        bookId = bookCartState.data!!.bookId,
                                        bookImageUrl = bookCartState.data!!.bookImageUrl,
                                        bookName = bookCartState.data!!.bookName,
                                        supplyTime = bookCartState.data!!.supplyTime,
                                        currentPrice = bookCartState.data!!.currentPrice,
                                        itemCount = bookCartState.data!!.itemCount + 1
                                    )
                                    Toast.makeText(context,"Sepetinizde ${bookCartState.data?.itemCount?.plus(1)} adet ${bookCartState.data?.bookName} bulunmaktadır.",Toast.LENGTH_SHORT).show()
                                    viewModel.updateCartItem(cart)

                                } else {
                                    dialogState = true
                                }


                            }) {
                            Row(
                                Modifier.fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    modifier = Modifier
                                        .size(45.dp)
                                        .weight(1.1f),
                                    imageVector = Icons.Rounded.Timer,
                                    contentDescription = "Add to cart",
                                    colorFilter = ColorFilter.tint(
                                        Color.White
                                    )
                                )
                                Text(
                                    modifier = Modifier
                                        .padding(end = 50.dp)
                                        .weight(8f),
                                    text = OnCart.ALREADY_ADDED.status,
                                    fontSize = 22.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }

                    } else {
                        //ürün sepette değil
                        Button(modifier = Modifier
                            .fillMaxSize(),
                            colors = ButtonDefaults.buttonColors(kitapyurduOrange), onClick = {
                                if (!viewModel.isUserOnline().isNullOrEmpty()) {
                                    val cart = Cart(
                                        cartId = UUID.randomUUID().toString(),
                                        userId = viewModel.isUserOnline().toString(),
                                        bookId = bookDetailState.data!!.bookId,
                                        bookImageUrl = bookDetailState.data!!.bookImageUrl,
                                        bookName = bookDetailState.data!!.bookName,
                                        supplyTime = bookDetailState.data!!.order.supplyTime,
                                        currentPrice = bookDetailState.data!!.price.currentPrice,
                                        itemCount = 1)

                                    viewModel.newCartItem(cart).also {
                                        Toast.makeText(context,"${bookDetailState.data?.bookName} sepetinize eklendi!",Toast.LENGTH_SHORT).show()
                                    }
                                } else {
                                    dialogState = true
                                }


                            }) {
                            Row(
                                Modifier.fillMaxSize(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    modifier = Modifier
                                        .size(45.dp)
                                        .weight(1.1f),
                                    imageVector = Icons.Rounded.Timer,
                                    contentDescription = "Add to cart",
                                    colorFilter = ColorFilter.tint(
                                        Color.White
                                    )
                                )
                                Text(
                                    modifier = Modifier
                                        .padding(end = 50.dp)
                                        .weight(8f),
                                    text = OnCart.ADD_CART.status,
                                    fontSize = 22.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
            is Resource.Error -> {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .background(kitapyurduOrange), contentAlignment = Alignment.BottomCenter
                ) {
                    //ürün sepette değil
                    Button(modifier = Modifier
                        .fillMaxSize(),
                        colors = ButtonDefaults.buttonColors(kitapyurduOrange), onClick = {
                            //todo sepete ekle, daha önce eklendiyse sayı artır
                        }) {
                        Row(
                            Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                modifier = Modifier
                                    .size(45.dp)
                                    .weight(1.1f),
                                imageVector = Icons.Rounded.Timer,
                                contentDescription = "Add to cart",
                                colorFilter = ColorFilter.tint(
                                    Color.White
                                )
                            )
                            Text(
                                modifier = Modifier
                                    .padding(end = 50.dp)
                                    .weight(8f),
                                text = OnCart.ADD_CART.status,
                                fontSize = 22.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }

    }
}

enum class OnCart(val status: String) {
    ADD_CART("Sepete Ekle"),
    ALREADY_ADDED("Sepetinizde +")
}


@Composable
fun Footer(viewModel: HomeViewModel, bookDetail: BookDetail) {
    StaticBanner(banner = viewModel.get3RandomStaticBanners.value[1])
    ReadStatus(bookDetail = bookDetail)
    StaticBanner(banner = viewModel.get3RandomStaticBanners.value[2])
}

@Composable
fun BookSection(
    bookDetail: BookDetail,
    likeState: MutableTransitionState<Like>,
    favBook: Resource<String?>,
    onAddFavorite: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Text(
            text = bookDetail.bookAuthor,
            style = TextStyle(
                color = Color.Black,
                fontWeight = FontWeight.Light,
                fontSize = 15.sp
            )
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = bookDetail.bookName,
            style = TextStyle(
                color = Color.Black,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 17.sp
            )
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = bookDetail.bookPublisher,
            style = TextStyle(
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
        )
    }
    //BOOK & PRİCE ROW
    Row(
        Modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .height(250.dp)
                .weight(0.5f)
        ) {

            //BOOK IMAGE
            val transition =
                updateTransition(transitionState = likeState, label = "Like Transition")

            val heartAlpha by transition.animateFloat(
                transitionSpec = {
                    when {
                        Like.Initial isTransitioningTo Like.Liked -> keyframes {
                            durationMillis = 500
                            0f at 0
                            0.5f at 255
                            1f at 400
                        }

                        Like.Liked isTransitioningTo Like.Gone -> tween(300)
                        else -> snap()
                    }
                },
                label = "Alpha"
            ) {
                when (it) {
                    Like.Liked -> 1f
                    else -> 0f
                }


            }


            val heartScale by transition.animateFloat(
                transitionSpec = {
                    when {
                        Like.Initial isTransitioningTo Like.Liked -> spring(dampingRatio = Spring.DampingRatioMediumBouncy)
                        Like.Liked isTransitioningTo Like.Gone -> tween(300)
                        else -> snap()
                    }
                },
                label = "Scale"
            ) {
                when (it) {
                    Like.Liked -> 3f
                    else -> 0f
                }
            }

            //FAVORITE ANIMATION
            if (likeState.currentState == Like.Initial) likeState.targetState = Like.Liked
            else if (likeState.currentState == Like.Liked) likeState.targetState = Like.Gone

            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp)
                    .scale(1f),
                painter = rememberImagePainter(data = bookDetail.bookImageUrl),
                contentDescription = bookDetail.bookName
            )

            Icon(
                Icons.Filled.Favorite, contentDescription = "Heart",
                tint = Color.Red,
                modifier = Modifier
                    .size(60.dp)
                    .align(Alignment.Center)
                    .graphicsLayer {
                        alpha = heartAlpha
                        scaleX = heartScale
                        scaleY = heartScale
                    }
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent), contentAlignment = Alignment.TopEnd
            ) {
                Box(modifier = Modifier.size(60.dp), contentAlignment = Alignment.Center) {
                    Image(
                        modifier = Modifier.size(60.dp),
                        painter = painterResource(id = R.drawable.disconut2),
                        contentDescription = "discount"
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Transparent), contentAlignment = Alignment.Center
                    ) {
                        Text(text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color = Color.White,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 12.sp
                                )
                            ) {
                                append("%")
                            }
                            withStyle(
                                style = SpanStyle(
                                    color = Color.White,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 16.sp
                                )
                            ) {
                                append(
                                    CalculateDiscount(
                                        bookDetail.price.currentPrice.toDouble(),
                                        bookDetail.price.oldPrice.toDouble()
                                    ) ?: ""
                                )
                            }
                            withStyle(
                                style = SpanStyle(
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                            ) {
                                append("\nindirim")
                            }
                        }, textAlign = TextAlign.Center)
                    }
                }
            }
        }
        //BOOK PRICE COLUMN
        Column(
            modifier = Modifier
                .weight(0.5f)
                .padding(end = 4.dp), horizontalAlignment = Alignment.End
        ) {
            RateBar(rate = bookDetail.rate)
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = bookDetail.price.oldPrice + " TL",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    textDecoration = TextDecoration.LineThrough
                )
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = bookDetail.price.currentPrice + " TL",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 23.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "6 puan kazandırır",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Normal
                )
            )
            Spacer(modifier = Modifier.height(6.dp))
            InteractionWithBook(if (favBook.data.isNullOrEmpty()) "Favorilerime Ekle" else "Favorilere Eklendi", Icons.Default.Favorite) {
                onAddFavorite()
            }
            InteractionWithBook("Alışveriş Listeme Ekle", Icons.Default.AddShoppingCart) {

            }
            InteractionWithBook("Fiyat Alarmına Ekle", Icons.Default.NotificationAdd) {

            }
            InteractionWithBook("Paylaş", Icons.Rounded.Share) {

            }
        }
    }
    OrderSupplyTime(order = bookDetail.order)
}

@Composable
fun InteractionWithBook(text: String, icon: ImageVector, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable {
                onClick()
            },
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = TextStyle(color = Color.DarkGray, fontSize = 13.sp)
        )
        Icon(
            modifier = Modifier
                .size(18.dp)
                .alpha(0.6f)
                .padding(start = 4.dp),
            imageVector = icon,
            contentDescription = text
        )
    }
    Spacer(modifier = Modifier.height(6.dp))
}

@Composable
fun OrderSupplyTime(order: Order) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.LightGray)
            .height(40.dp)
            .background(Color.LightGray),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = if (order.preOrder) Arrangement.Start else Arrangement.SpaceEvenly
    ) {

        if (order.preOrder) {
            Text(
                text = "Ön Sipariş: ${order.supplyTime} gün sonra sevkedilebilir.",
                style = TextStyle(
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            )
            Icon(
                modifier = Modifier
                    .size(18.dp)
                    .padding(start = 3.dp),
                imageVector = Icons.Filled.Info,
                contentDescription = "Info"
            )
        } else {
            Text(text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.Black,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 14.sp
                    )
                ) {
                    append("  Tedarik Süresi:  ")
                }
                withStyle(
                    style = SpanStyle(
                        color = Color.Black,
                        fontWeight = FontWeight.Light,
                        fontSize = 14.sp
                    )
                ) {
                    append(order.supplyTime)
                }
                withStyle(
                    style = SpanStyle(
                        color = Color.Red,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 14.sp
                    )
                ) {
                    append(" Stok Miktarı: ${order.stockAmount}+  ")
                }
            })
        }
    }
}


