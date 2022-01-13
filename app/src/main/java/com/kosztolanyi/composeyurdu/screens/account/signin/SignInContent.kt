package com.kosztolanyi.composeyurdu.screens.account.signin

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.kosztolanyi.composeyurdu.components.bottombar.BottomBarScreen
import com.kosztolanyi.composeyurdu.components.button.KitapyurduLargeButton
import com.kosztolanyi.composeyurdu.navigation.Destinations
import com.kosztolanyi.composeyurdu.ui.theme.kitapyurduGray
import com.kosztolanyi.composeyurdu.ui.theme.kitapyurduOrange
import kotlin.math.sign

@ExperimentalComposeUiApi
@Composable
fun SignInContent(viewModel: SignInViewModel, navHostController: NavHostController) {
    var openDialog by remember { mutableStateOf(false) }
    var signInState = viewModel.signInState
    val context = LocalContext.current

    LaunchedEffect(key1 = signInState.value, block = {
        when(signInState.value){
            SignInState.LOADING -> {
                println("sign in loadign")
            }
            SignInState.SUCCESS -> {
                println("sign in success")
                navHostController.navigate(BottomBarScreen.Home.route)
            }
            SignInState.ERROR -> {
                println("sign in error")
            }
            else -> {
                println("sign in idle")
            }
        }
    })


    Column(
        Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
            .padding(horizontal = 16.dp)
    ) {
        LoginItem(
            fieldText = "E-posta Adresi",
            onText = viewModel.mailAddress.value,
            onTextChange = {
                viewModel.mailAddress.value = it
            },
            keyboardType = KeyboardType.Email
        )
        LoginItem(
            fieldText = "Şifre",
            onText = viewModel.password.value,
            onTextChange = {
                viewModel.password.value = it
            },
            keyboardType = KeyboardType.Password,
            visualTransformation = PasswordVisualTransformation()
        )
        KitapyurduLargeButton(text = "Giriş Yap") {
            //Todo onButtonClicked
            viewModel.signIn().also {
                if (signInState.value == SignInState.SUCCESS){
                    Toast.makeText(context,"Tekrar Hoş geldiniz!",Toast.LENGTH_SHORT).show()
                    navHostController.navigate(BottomBarScreen.Home.route)
                }
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
        LoginOutlinedButton(
            "Üye Ol",
            kitapyurduOrange,
            borderStroke = BorderStroke(width = 1.dp, color = kitapyurduOrange)
        ){
            //Todo onclick Sign Up
            navHostController.navigate(Destinations.SignUp.route)
        }
        Spacer(modifier = Modifier.height(16.dp))
        LoginOutlinedButton(
                "Şifremi unuttum",
        Color.LightGray,
        borderStroke = BorderStroke(width = 1.dp, color = Color.LightGray)
        ){
        //Todo onclick Forgotten Password
            openDialog = true
    }
    }

    if (openDialog){
        ForgotPasswordDialog(viewModel = viewModel, onPasswordResetMailSend = {
            ////Todo onclick Send Password Reset E-mail
        }){
            openDialog = false
        }
    }
}

@Composable
fun LoginOutlinedButton(
    text: String,
    buttonContentColor: Color,
    borderStroke: BorderStroke,
    onClick: () -> Unit
) {
    Spacer(modifier = Modifier.height(6.dp))
    OutlinedButton(
        onClick = { onClick() },
        Modifier
            .background(Color.White)
            .height(50.dp),
        border = borderStroke
    ) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = buttonContentColor
            )
            Icon(
                imageVector = Icons.Rounded.ArrowForward,
                contentDescription = text,
                tint = buttonContentColor
            )
        }
    }
    Spacer(modifier = Modifier.height(6.dp))
}

@Composable
fun LoginItem(
    fieldText: String,
    onText: String,
    onTextChange: (String) -> Unit,
    keyboardType: KeyboardType,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 12.dp)
    ) {
        Text(text = fieldText, fontSize = 13.sp)
        Spacer(modifier = Modifier.height(3.dp))
        OutlinedTextField(

            modifier = Modifier.fillMaxWidth(),
            value = onText,
            onValueChange = {
                onTextChange(it)
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = ImeAction.Next
            ),
            visualTransformation = visualTransformation,
            trailingIcon = {
                if (onText.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Rounded.Cancel,
                        contentDescription = "Delete",
                        modifier = Modifier.clickable {
                            onTextChange("")
                        })
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = kitapyurduOrange,
                focusedBorderColor = Color.LightGray,
                unfocusedBorderColor = Color.LightGray
            ),
            textStyle = TextStyle(fontWeight = FontWeight.Bold)

        )
    }
}