package com.kosztolanyi.composeyurdu.components.ratebar

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RateBar(rate: Int, arrangement : Arrangement.Horizontal = Arrangement.End) {
    Row(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth(), horizontalArrangement = arrangement
    ) {
        for (i in 1..5) {
            Icon(
                modifier = Modifier.size(19.dp),
                imageVector = Icons.Filled.StarRate,
                contentDescription = "Rate",
                tint = if (i <= rate) Color(0xFFFFD700) else Color(0xFFA2ADB1)
            )
        }
    }
}
