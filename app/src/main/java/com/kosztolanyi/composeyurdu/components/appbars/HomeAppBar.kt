package com.kosztolanyi.composeyurdu.components.appbars

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PersonOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kosztolanyi.composeyurdu.R
import com.kosztolanyi.composeyurdu.ui.theme.*

@Composable
fun HomeTopAppBar(isUserSignedIn: Boolean, onAccountClicked: () -> Unit) {
    TopAppBar(
        backgroundColor = kitapyurduGray,
        contentPadding = PaddingValues(0.dp),
        elevation = 0.dp
    ) {
        Image(
            modifier = Modifier
                .fillMaxHeight()
                .width(40.dp),
            painter = painterResource(id = R.drawable.ky_icon),
            contentDescription = "Kitapyurdu İkon"
        )
        Text(
            text = "kitapyurdu",
            style = TextStyle(
                color = kitapyurduGreen,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 27.sp
            ),
            textAlign = TextAlign.Center
        )
        if (isUserSignedIn) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 16.dp)
                    .clickable {
                        onAccountClicked()
                    },
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Hesabım",
                    style = TextStyle(
                        color = kitapyurduAccountColor,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Light
                    )
                )
                Icon(
                    modifier = Modifier.size(30.dp),
                    imageVector = Icons.Rounded.PersonOutline,
                    contentDescription = "Person",
                    tint = kitapyurduAccountColor
                )
            }
        }
    }
}