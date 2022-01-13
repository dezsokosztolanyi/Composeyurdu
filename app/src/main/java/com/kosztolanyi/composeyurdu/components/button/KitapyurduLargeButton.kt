package com.kosztolanyi.composeyurdu.components.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kosztolanyi.composeyurdu.ui.theme.kitapyurduOrange

@Composable
fun KitapyurduLargeButton(text : String,paddingValues: PaddingValues = PaddingValues(8.dp), onButtonClicked: () -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues = paddingValues)
            .clip(RectangleShape),
        colors = ButtonDefaults.buttonColors(kitapyurduOrange),
        onClick = {
            onButtonClicked()
        }
    ) {
        Text(
            modifier = Modifier.padding(5.dp),
            text = text,
            fontSize = 20.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}