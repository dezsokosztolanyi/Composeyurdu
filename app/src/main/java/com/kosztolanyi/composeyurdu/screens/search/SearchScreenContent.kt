package com.kosztolanyi.composeyurdu.screens.search

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.kosztolanyi.composeyurdu.navigation.Destinations
import com.kosztolanyi.composeyurdu.ui.theme.DarkGray
import com.kosztolanyi.composeyurdu.ui.theme.kitapyurduOrange
import com.kosztolanyi.composeyurdu.ui.theme.kitapyurduGray

@ExperimentalComposeUiApi
@Composable
fun SearchScreenContent(
    text: String,
    viewModel: SearchViewModel,
    navHostController: NavHostController
) {
    val context = LocalContext.current
    Column(
        Modifier
            .fillMaxSize()
            .padding(bottom = 45.dp)) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp), horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                SearchIconButton(text = "Barkod ile Ara", icon = Icons.Default.Menu) {
                    //Todo Scan Search
                    navHostController.navigate(Destinations.Barcode.route)

                }
                SearchIconButton(text = "Ses ile ara", icon = Icons.Default.Mic) {
                    //Todo Voice Search
                    Toast.makeText(context,"Yakında",Toast.LENGTH_SHORT).show()
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            SearchField(text = text, onTextChange = viewModel.searchQuery.component2())
            Spacer(modifier = Modifier.height(6.dp))
        }
        Divider()
        Spacer(modifier = Modifier.height(10.dp))
        Box(Modifier.fillMaxWidth()) {
            LazyColumn(contentPadding = PaddingValues(16.dp)) {
                items(getSearchList()) { item ->
                    if (item.equals(getSearchList().first())) {
                        LazyColumnItemHeader(text = item)
                    } else {
                        LazyColumnItem(text = item){
                            //Todo search clicked
                        }
                    }
                }
            }
        }
    }

}

fun getSearchList(): List<String> {
    return listOf(
        "Popüler Aramalar",
        "is bankasi",
        "can yayinlari",
        "cocuk",
        "ingilizce",
        "cocuk kitaplari",
        "kpss",
        "stefan zweig",
        "dostoyevski",
        "yaşar kemal",
        "harry potter"
    )
}

@Composable
fun LazyColumnItemHeader(text: String) {
    Column(Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier.padding(vertical = 4.dp),
            text = text,
            style = TextStyle(
                color = Color.Black,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(3.dp))
        Divider()
    }
}

@Composable
fun LazyColumnItem(text: String,onSearchClicked: (String) -> Unit) {
    Column(
        Modifier
            .fillMaxWidth()
            .clickable { onSearchClicked(text) }) {
        Text(
            modifier = Modifier.padding(vertical = 4.dp),
            text = text,
            style = TextStyle(
                color = Color.DarkGray,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        )
        Spacer(modifier = Modifier.height(3.dp))
        Divider()
    }
}

@Composable
fun SearchIconButton(text: String, icon: ImageVector, onClick: () -> Unit) {
    Button(
        modifier = Modifier.width(160.dp),
        onClick = {
            onClick()
        },
        colors = ButtonDefaults.buttonColors(
            kitapyurduGray
        ),
        elevation = ButtonDefaults.elevation(0.dp)
    ) {
        Icon(
            modifier = Modifier.weight(1f),
            imageVector = icon,
            contentDescription = text,
            tint = DarkGray
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(modifier = Modifier.weight(3f), text = text, fontSize = 11.sp, color = DarkGray)
    }
}

@ExperimentalComposeUiApi
@Composable
fun SearchField(text: String, onTextChange: (String) -> Unit) {
    val keyboard = LocalSoftwareKeyboardController.current
    OutlinedTextField(
        value = text, onValueChange = {
            onTextChange(it)
        },
        modifier = Modifier
            .fillMaxWidth(),
        placeholder = {
            Text(
                text = "kitap, yazar, yayınevi ara",
                color = Color.DarkGray,
                fontWeight = FontWeight.Bold
            )
        },
        singleLine = true,
        textStyle = TextStyle(color = Color.Black, fontWeight = FontWeight.Bold),
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "")
        },
        trailingIcon = {
            if (text.isNotEmpty()) {
                Icon(
                    imageVector = Icons.Default.Cancel,
                    contentDescription = "Delete",
                    modifier = Modifier.clickable {
                        onTextChange("")
                        keyboard?.hide()
                    })
            }
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = Color.White,
            cursorColor = kitapyurduOrange,
            focusedBorderColor = kitapyurduOrange,
            unfocusedBorderColor = kitapyurduOrange
        )
    )
}