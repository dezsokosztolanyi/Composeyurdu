package com.kosztolanyi.composeyurdu.screens.detail.tab

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kosztolanyi.composeyurdu.models.mybook.BookDetail

@Composable
fun Tags(bookDetail: BookDetail,pagerSize: (Dp) -> Unit) {
    pagerSize(250.dp)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 6.dp)
    ) {
        if (bookDetail.tag.translator?.isNotBlank() == true){
            Tag(title = "Çevirmen", content = bookDetail.tag.translator)
        }
        Tag(title = "Yayın Tarihi", content = bookDetail.tag.publishDate)
        Tag(title = "ISBN", content = bookDetail.tag.isbn)
        Tag(title = "Dil", content = bookDetail.tag.dil)
        Tag(title = "Sayfa Sayısı", content = bookDetail.tag.sayfaSayisi.toString())
        Tag(title = "Cilt Tipi", content = bookDetail.tag.ciltTipi)
        Tag(title = "Kağıt Cinsi", content = bookDetail.tag.kagitCinsi)
        Tag(title = "Boyut", content = bookDetail.tag.size)

        SatisAdedi(bookDetail.tag.satilmaSayisi)
    }
}

@Composable
fun Tag(title: String, content: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 2.dp)
    ) {
        Row(Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.width(120.dp),
                text = title,
                style = TextStyle(
                    color = Color.Black,
                    fontWeight = FontWeight.ExtraBold
                )
            )
            Text(
                text = content,
                style = TextStyle(
                    color = Color.Black,
                    fontWeight = FontWeight.Light
                )
            )
        }
        Divider(
            Modifier
                .fillMaxWidth()
                .padding(top = 3.dp), Color.LightGray, 0.5.dp
        )
    }
}

@Composable
fun SatisAdedi(satisAdedi : Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 2.dp)
    ) {
            Text(
                text = "Bu üründen ${satisAdedi} adet satılmıştır",
                style = TextStyle(
                    color = Color.Black,
                    fontWeight = FontWeight.Light
                )
            )
        Divider(
            Modifier
                .fillMaxWidth()
                .padding(top = 3.dp), Color.LightGray, 0.5.dp
        )
    }
}

@Preview
@Composable
fun TagPreview() {
    Tag(title = "Çevirmen", content = "Dost Körpe")
}