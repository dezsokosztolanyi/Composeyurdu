package com.kosztolanyi.composeyurdu.screens.detail.tab

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter

@Composable
fun AllComments() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
//        CommentsScreen()
//        CommentsScreen()
//        CommentsScreen()
        //todo comments
        Text(text = "Yakında")
    }

}

//comments section
@Composable
fun CommentsScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        //Image & name Row
        ImageAndName()
        //Box
        Comments()
        //like dislike row
        LikeDislike()
    }
}

@Composable
fun LikeDislike() {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 6.dp, horizontal = 14.dp)) {
        Icon(modifier = Modifier.size(24.dp), imageVector = Icons.Default.Done, contentDescription = "Like")
        Text(modifier = Modifier.padding(start = 4.dp, end = 8.dp),text = "(4)", style = TextStyle(color = Color.LightGray, fontWeight = FontWeight.Light))
        Icon(modifier = Modifier.size(24.dp), imageVector = Icons.Default.Warning, contentDescription = "Like")
        Text(modifier = Modifier.padding(start = 4.dp),text = "(0)", style = TextStyle(color = Color.LightGray, fontWeight = FontWeight.Light))
    }
}

@Composable
fun Comments() {
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(Color.LightGray)
        .border(BorderStroke(1.dp, Color.LightGray), RoundedCornerShape(8.dp))){
        //date rate bought/not bought
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(0.3f), text = "06 Aralık 2021", style = TextStyle(color = Color.Gray))
            Text(modifier = Modifier.fillMaxWidth(0.8f), text = "Rate Bar", style = TextStyle(color = Color.Gray, textAlign = TextAlign.Start))
            Icon(modifier = Modifier.size(20.dp),imageVector = Icons.Default.ShoppingCart, contentDescription = "Bought", tint = Color.Yellow)
        }
        //comment body
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp, bottom = 12.dp, start = 8.dp, end = 8.dp)) {
            Text(text = "Comments fier.fillMaxWidth(0.8f), text = \"Rate Bar\", style = TextStyle(color = C asdas dasasd asd", style = TextStyle(color = Color.Black, fontWeight = FontWeight.Light), maxLines = 2, overflow = TextOverflow.Ellipsis)
        }
    }
}

@Composable
fun ImageAndName() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
            .padding(vertical = 4.dp,)
        , verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier
                .align(CenterVertically)
                .size(30.dp)
                .clip(CircleShape)
            ,
            painter = rememberImagePainter(data = "https://img.kitapyurdu.com/v1/getImage/fn:/wi:40/wh:3fcd03177/iti:11"),
            contentDescription = "Person"
        )
        Spacer(modifier = Modifier.width(20.dp))
        Text(text = "Some User", style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold))
    }
}

@Preview
@Composable
fun CommentSectionPreview() {
    CommentsScreen()
}