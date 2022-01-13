package com.kosztolanyi.composeyurdu.components.alertdialog

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun CustomAlertDialog(
    title: String,
    content: String,
    positiveAnswer: String,
    negativeAnswer: String,
    onPositiveAnswerClicked: () -> Unit,
    onNegativeAnswerClicked: () -> Unit,
    onDismissed : () -> Unit
) {

        AlertDialog(
            onDismissRequest = { onDismissed() },
            title = {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            },
            text = {
                Text(
                    text = content,
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Light
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    onPositiveAnswerClicked()
//                    navHostController.navigate(navigateDirection) {
//                        popUpTo(navHostController.graph.findStartDestination().id)
//                        launchSingleTop = true
//                    }
                }) {
                    Text(
                        text = positiveAnswer,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    onNegativeAnswerClicked()
//                    navHostController.navigateUp()
                }) {
                    Text(
                        text = negativeAnswer,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
            }
        )
    }


