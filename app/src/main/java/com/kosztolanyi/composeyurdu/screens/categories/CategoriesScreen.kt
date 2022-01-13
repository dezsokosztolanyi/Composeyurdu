package com.kosztolanyi.composeyurdu.screens.categories

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kosztolanyi.composeyurdu.components.appbars.MainTopAppBar
import com.kosztolanyi.composeyurdu.ui.theme.DarkGray
import com.kosztolanyi.composeyurdu.ui.theme.kitapyurduGreen

@Composable
fun CategoriesScreen() {
    val context = LocalContext.current

    Scaffold(topBar = {
        MainTopAppBar(topBarTitle = "Kategoriler")
    }) {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(contentPadding = PaddingValues(bottom = 60.dp)) {
                items(getCategoryItems()) { item ->
                    CategoryItem(categoryName = item) { categoryName ->
                        Toast.makeText(context,categoryName,Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryItem(categoryName: String, onClick: (String) -> Unit) {
    Column(
        Modifier
            .fillMaxWidth()
            .clickable {
                onClick(categoryName)
            },
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = categoryName,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                ),
                modifier = Modifier
                    .padding(vertical = 18.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Icon(
                modifier = Modifier.size(30.dp),
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "",
                tint = Color.Black
            )
            Text(
                modifier = Modifier
                .fillMaxWidth(),

                text = "Tümü",
                style = TextStyle(
                    color = kitapyurduGreen,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                ),
                textAlign = TextAlign.End )
        }

        Divider()
    }
}

fun getCategoryItems(): List<String> {
    return listOf(
        "Kitap",
        "Oyun",
        "Çeşitli",
        "Aksesuar",
        "Poster",
        "CD",
        "Müzik",
        "Film",
        "Oyuncak",
        "Teknoloji",
    )
}