package com.kosztolanyi.composeyurdu.screens.account.signup

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.kosztolanyi.composeyurdu.ui.theme.LightGray
import com.kosztolanyi.composeyurdu.ui.theme.kitapyurduOrange

@Composable
fun SignUpTopAppBar(navHostController: NavHostController) {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        navigationIcon = { SignUpBackAction(onBackClicked = {
            navHostController.popBackStack()
        }) },
        title = {
            Text(
                text = "Hesap Oluştur",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }, backgroundColor = LightGray)
}

@Composable
fun SignUpBackAction(
    onBackClicked: () -> Unit
) {
    IconButton(onClick = { onBackClicked() }) {
        Icon(
            imageVector = Icons.Filled.ArrowBack, contentDescription = "Back",
            tint = kitapyurduOrange
        )
    }
}