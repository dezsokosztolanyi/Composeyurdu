package com.kosztolanyi.composeyurdu.screens.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.More
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.kosztolanyi.composeyurdu.models.Cart
import com.kosztolanyi.composeyurdu.ui.theme.DarkGray
import com.kosztolanyi.composeyurdu.ui.theme.kitapyurduGray

@Composable
fun CartItem(cart: Cart, onItemClicked : (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
            .padding(vertical = 2.dp)
            .clickable {
                onItemClicked(cart.bookId)
            }
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
        ) {
            Image(
                modifier = Modifier
                    .weight(2f)
                    .fillMaxSize()
                    .padding(vertical = 10.dp),
                painter = rememberImagePainter(data = cart.bookImageUrl) {
                    scale(Scale.FILL)
                },
                contentDescription = "Book Image"
            )
            Column(
                modifier = Modifier
                    .weight(3.5f)
                    .fillMaxHeight(), verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = cart.bookName ?: "asdas",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Miktar: ${cart.itemCount ?: "asdas"}",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Light
                )
                Text(
                    text = "Fiyat: ${cart.currentPrice ?: "asdas"} TL",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Light
                )
                Text(
                    text = cart.supplyTime ?: "asdas",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Light
                )
            }
            Column(
                modifier = Modifier
                    .weight(1.5f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.End
            ) {
                Icon(imageVector = Icons.Default.More, contentDescription = "More", tint = DarkGray)
                Text(
                    text = roundDecimal((cart.currentPrice.toDouble() * cart.itemCount)) + " TL",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Divider(modifier = Modifier.fillMaxWidth().padding(top = 2.dp), color = kitapyurduGray)
    }
}

fun roundDecimal(price : Double) : String{
    return String.format("%.2f",price)
}