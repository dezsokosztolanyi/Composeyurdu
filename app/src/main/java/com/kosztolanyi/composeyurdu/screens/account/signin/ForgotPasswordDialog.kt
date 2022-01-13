package com.kosztolanyi.composeyurdu.screens.account.signin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.kosztolanyi.composeyurdu.components.button.KitapyurduLargeButton
import com.kosztolanyi.composeyurdu.ui.theme.kitapyurduGray
import com.kosztolanyi.composeyurdu.ui.theme.kitapyurduOrange

@ExperimentalComposeUiApi
@Composable
fun ForgotPasswordDialog(viewModel: SignInViewModel, onPasswordResetMailSend: () -> Unit, onDialogClosed: () -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val forgotPassword = viewModel.forgotPassword

    Dialog(onDismissRequest = { onDialogClosed() }) {
        androidx.compose.material.Surface(Modifier.fillMaxWidth(),color = Color.White, shape = RoundedCornerShape(4.dp)) {
            Column(
                Modifier
                    .fillMaxWidth()

            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .background(kitapyurduGray)
                        .padding(vertical = 10.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Şifremi Unuttum",
                        style = TextStyle(
                            color = kitapyurduOrange,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)) {
                    Spacer(modifier = Modifier.height(10.dp))
                    LoginItem(fieldText = "E-posta Adresi:",
                        onText = forgotPassword.value,
                        onTextChange = {
                            forgotPassword.value = it
                        }, keyboardType = KeyboardType.Email
                    )
                    Spacer(modifier = Modifier.height(18.dp))
                    KitapyurduLargeButton(text = "Gönder") {
                        onPasswordResetMailSend()
                        keyboardController?.hide()
                        onDialogClosed()
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

    }
}













