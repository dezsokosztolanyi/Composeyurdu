package com.kosztolanyi.composeyurdu.screens.account.signin

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.kosztolanyi.composeyurdu.ui.theme.LightGray
import com.kosztolanyi.composeyurdu.ui.theme.kitapyurduOrange

@Composable
fun SignInTopAppBar(onBackClicked: () -> Unit) {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        navigationIcon = { SignInBackAction(onBackClicked = onBackClicked) },
        title = {
            Text(
                text = "Üye Girişi",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            )
        }, backgroundColor = LightGray
    )
}

@Composable
fun SignInBackAction(
    onBackClicked: () -> Unit
) {
    IconButton(onClick = { onBackClicked() }) {
        Icon(
            imageVector = Icons.Filled.ArrowBack, contentDescription = "Back",
            tint = kitapyurduOrange
        )
    }
}