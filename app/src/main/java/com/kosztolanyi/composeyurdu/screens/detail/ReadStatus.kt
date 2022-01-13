package com.kosztolanyi.composeyurdu.screens.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kosztolanyi.composeyurdu.models.HomeDataItem
import com.kosztolanyi.composeyurdu.models.mybook.BookDetail
import com.kosztolanyi.composeyurdu.ui.theme.TextColor

@Composable
fun ReadStatus(bookDetail: BookDetail) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        Status(icon = Icons.Default.WatchLater, status = "Okuyacağım", count = 12)
        Status(icon = Icons.Filled.LibraryBooks, status = "Okuyorum", count = 23)
        Status(icon = Icons.Filled.DoneOutline, status = "Okudum", count = 6)
    }
}

@Composable
fun Status(icon: ImageVector, status: String, count: Int) {
    Column(
        modifier = Modifier
            .width(110.dp).padding(10.dp)
        , horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(3.dp)),
            backgroundColor = TextColor
        ) {
            Column(
                modifier = Modifier.fillMaxSize().padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(imageVector = icon, contentDescription = status, tint = Color.Gray)
                Text(
                    text = status,
                    style = TextStyle(
                        color = Color.Gray,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Light
                    )
                )
            }
        }
        Text(
            modifier = Modifier.padding(vertical = 4.dp),
            text = "${count} kişi",
            style = TextStyle(
                color = Color.Gray
                , fontSize = 10.sp,
                fontWeight = FontWeight.Normal)
        )
    }
}