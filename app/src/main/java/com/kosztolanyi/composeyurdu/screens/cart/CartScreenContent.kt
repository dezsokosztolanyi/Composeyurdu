package com.kosztolanyi.composeyurdu.screens.cart

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.kosztolanyi.composeyurdu.components.alertdialog.CustomAlertDialog
import com.kosztolanyi.composeyurdu.components.button.KitapyurduLargeButton
import com.kosztolanyi.composeyurdu.models.CartPriceItem
import com.kosztolanyi.composeyurdu.navigation.Destinations
import com.kosztolanyi.composeyurdu.ui.theme.kitapyurduBottomBarColor
import com.kosztolanyi.composeyurdu.ui.theme.kitapyurduOrange
import com.kosztolanyi.composeyurdu.util.Resource

@Composable
fun CartScreenContent(viewModel: CartViewModel, navHostController: NavHostController) {
    val cartResponse = viewModel.cartResponse.collectAsState()
    val userId = viewModel.isUserOnline()
    val dialogState = remember {
        mutableStateOf(true)
    }
    var cartPriceItem by remember {
        mutableStateOf<CartPriceItem>(CartPriceItem())
    }
    var context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 56.dp)
    ) {

        if (userId != null) {

            when (cartResponse.value) {
                is Resource.Success -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(4f)
                    ) {
                        if (!cartResponse.value.data.isNullOrEmpty()) {
                            items(cartResponse.value.data!!) { cart ->
                                println(cart.toString())
                                CartItem(cart = cart) { bookId ->
                                    navHostController.navigate(Destinations.Detail.route + "/$bookId")
                                }
                            }
                        }
                    }
                    if (!cartResponse.value.data.isNullOrEmpty()){

                        LaunchedEffect(key1 = true, key2 = cartResponse, block = {
                            cartPriceItem = viewModel.calculator(cartResponse.value.data!!)
                        })

                        Column(
                            Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            Row(
                                Modifier
                                    .fillMaxSize()
                                    .weight(1f)
                                    .background(kitapyurduBottomBarColor)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .weight(1f)
                                        .padding(top = 8.dp)
                                        .padding(start = 6.dp),
                                    contentAlignment = Alignment.TopStart
                                ) {

                                    Text(
                                        text = cartPriceItem.itemCount.toString() + " ürün",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.Black
                                    )
                                }
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .weight(5f)
                                        .padding(end = 8.dp),
                                    horizontalAlignment = Alignment.End,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    CartTotalAmountItem(title = "Ara Toplam:", amount = roundDecimalAmount(cartPriceItem.amount) + " TL")
                                    CartTotalAmountItem(title = "Toplam:", amount = roundDecimalAmount(cartPriceItem.amount) + " TL")
                                    CartTotalAmountItem(
                                        title = "Kazanacağınız Puan:",
                                        amount = cartPriceItem.bonusPoints.toString()
                                    )
                                }
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .weight(0.8f)
                            ) {
                                KitapyurduLargeButton(
                                    text = "Satın Al",
                                    paddingValues = PaddingValues(0.dp)
                                ) {
//                                    navHostController.navigate(Destinations.Payment.route)
                                    Toast.makeText(context,"Yakında",Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }

                }
                is Resource.Loading -> {
                    println("Loading")
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = kitapyurduOrange)
                    }

                }
                is Resource.Error -> {
                    println("e")
                    println(cartResponse.value.message.toString())
                }
            }
        } else {
            if (dialogState.value){
                CustomAlertDialog(
                    title = "Uyarı",
                    content = "Bu işlemi yapabilmeniz için oturum açmanız gerekir.",
                    positiveAnswer = "Giriş Yap / Üye ol",
                    negativeAnswer = "İptal",
                    onPositiveAnswerClicked = {
                        dialogState.value = false
                        navHostController.navigate(Destinations.SignIn.route)
                    },
                    onNegativeAnswerClicked = {
                        navHostController.popBackStack()
                    },
                    onDismissed = {
//                            dialogState.value = false
                    })
            }
        }
    }
}

@Composable
fun CartTotalAmountItem(title: String, amount: String) {
    Row(
        modifier = Modifier.widthIn(min = 200.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title, fontSize = 13.sp, fontWeight = FontWeight.Light, color = Color.Black)
        Text(text = amount, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.Black)

    }
}
fun roundDecimalAmount(price : Double) : String{
    return String.format("%.2f",price)
}