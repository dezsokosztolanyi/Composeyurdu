package com.kosztolanyi.composeyurdu.screens.account.account

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.kosztolanyi.composeyurdu.R
import com.kosztolanyi.composeyurdu.components.alertdialog.CustomAlertDialog
import com.kosztolanyi.composeyurdu.models.User
import com.kosztolanyi.composeyurdu.navigation.Destinations
import com.kosztolanyi.composeyurdu.screens.account.signin.LoginOutlinedButton
import com.kosztolanyi.composeyurdu.ui.theme.DarkGray
import com.kosztolanyi.composeyurdu.ui.theme.kitapyurduOrange
import kotlinx.coroutines.launch

@Composable
fun AccountContent(navHostController: NavHostController, viewModel: AccountViewModel) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val user = viewModel.user
    val dialogState = remember {
        mutableStateOf(false)
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp), contentAlignment = Alignment.Center){
        LaunchedEffect(key1 = true, block = {
            viewModel.isUserOnline()
            println("isUserOnlineCalled")
        })
        if (dialogState.value){
            Box(modifier = Modifier.fillMaxSize()){
                CustomAlertDialog(
                    title = "Uyarı",
                    content = "Çıkış işlemi yapılacaktır",
                    positiveAnswer = "Tamam",
                    negativeAnswer = "İptal",
                    onPositiveAnswerClicked = {
                        dialogState.value = false
                        viewModel.viewModelScope.launch {
                            viewModel.exitAccount()
                        }
                    },
                    onNegativeAnswerClicked = {
                        dialogState.value = false
                    },
                    onDismissed = {
                        dialogState.value = false
                    })
            }
        }

    Column(
        Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
    ) {
        if (viewModel.loadingState){
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                CircularProgressIndicator(color = kitapyurduOrange)
            }
        }else{

            if (user != null) {
                println("User not null $user.emailAddress")
                OnlineUserAccount(user = user, context = context){
                    dialogState.value = true
                }
            } else {
                println("User null")
                ButtonWith2Icon(
                    Icons.Rounded.PersonPinCircle,
                    Icons.Rounded.ArrowRight,
                    navHostController = navHostController
                )
            }
        }

        AccountContact()
        Spacer(modifier = Modifier.height(24.dp))
        SocialMediaButtons()
        Spacer(modifier = Modifier.height(120.dp))
        Text(
            text = "ver. 6.30.6(19693)",
            fontSize = 10.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            textAlign = TextAlign.Center,
            color = Color.LightGray
        )
        Spacer(modifier = Modifier.height(60.dp))
    }
}
}

@Composable
fun OnlineUserAccount(context: Context, user: User, onExitClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(top = 8.dp, bottom = 16.dp)
            .clip(RoundedCornerShape(4.dp))
            .border(width = 1.dp, color = Color.LightGray),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier
                .size(60.dp)
                .padding(end = 8.dp),
            imageVector = Icons.Rounded.PersonOutline,
            contentDescription = "User Picture",
            tint = kitapyurduOrange
        )

        //todo username
        Text(
            text = user.username +" "+ user.lastname,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = kitapyurduOrange
        )
        AccountSettings {
            //todo hesap ayarları tıklandı
        }
    }
    Column(
        Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        LoginOutlinedButton(
            text = "Siparişlerim",
            buttonContentColor = kitapyurduOrange,
            borderStroke = BorderStroke(width = 1.dp, kitapyurduOrange)
        ) {
            Toast.makeText(context, "Siparişlerim", Toast.LENGTH_SHORT).show()
        }
        LoginOutlinedButton(
            text = "Favorilerim",
            buttonContentColor = kitapyurduOrange,
            borderStroke = BorderStroke(width = 1.dp, kitapyurduOrange)
        ) {
            Toast.makeText(context, "Favorilerim", Toast.LENGTH_SHORT).show()
        }
        LoginOutlinedButton(
            text = "Alışveriş Listem",
            buttonContentColor = kitapyurduOrange,
            borderStroke = BorderStroke(width = 1.dp, kitapyurduOrange)
        ) {
            Toast.makeText(context, "Alışveriş Listem", Toast.LENGTH_SHORT).show()
        }
        LoginOutlinedButton(
            text = "Fiyat Alarmı Listem",
            buttonContentColor = kitapyurduOrange,
            borderStroke = BorderStroke(width = 1.dp, kitapyurduOrange)
        ) {
            Toast.makeText(context, "Fiyat Alarmı Listem", Toast.LENGTH_SHORT).show()
        }
        LoginOutlinedButton(
            text = "Adreslerim",
            buttonContentColor = kitapyurduOrange,
            borderStroke = BorderStroke(width = 1.dp, kitapyurduOrange)
        ) {
            Toast.makeText(context, "Adreslerim", Toast.LENGTH_SHORT).show()
        }
        LoginOutlinedButton(
            text = "Hediye Kartı Aktivasyonu",
            buttonContentColor = kitapyurduOrange,
            borderStroke = BorderStroke(width = 1.dp, kitapyurduOrange)
        ) {
            Toast.makeText(context, "Hediye Kartı Aktivasyonu", Toast.LENGTH_SHORT).show()
        }
        LoginOutlinedButton(
            text = "Platin Üyelik",
            buttonContentColor = kitapyurduOrange,
            borderStroke = BorderStroke(width = 1.dp, kitapyurduOrange)
        ) {
            Toast.makeText(context, "Platin Üyelik", Toast.LENGTH_SHORT).show()
        }
    }
    ExitButtonWithoutBorder {
        onExitClicked()
    }
}

@Composable
fun AccountSettings(onAccountSettingsClicked: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .clickable {
                onAccountSettingsClicked()
            },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(16.dp),
            imageVector = Icons.Rounded.Settings,
            contentDescription = "Settings",
            tint = DarkGray
        )
        Text(
            text = "Hesap Ayarları",
            color = DarkGray,
            fontWeight = FontWeight.Light,
            fontSize = 13.sp
        )
    }
}

@Composable
fun ExitButtonWithoutBorder(onExitClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable {
                onExitClicked()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Çıkış Yap",
            color = Color.LightGray,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = "Exit",
            tint = Color.LightGray
        )
    }
}

@Composable
fun SocialMediaButtons() {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        iconList().forEach { icon ->
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            )
        }
    }
}

fun iconList(): List<Int> {
    return listOf(
        R.drawable.whatsapp,
        R.drawable.twitter,
        R.drawable.youtube,
        R.drawable.facebook

    )
}

@Composable
fun ButtonWith2Icon(
    leadingIcon: ImageVector,
    trailingIcon: ImageVector,
    navHostController: NavHostController
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .clickable {
                    navHostController.navigate(Destinations.SignIn.route)
                },
            colors = ButtonDefaults.buttonColors(
                kitapyurduOrange
            ),
            elevation = ButtonDefaults.elevation(0.dp),
            onClick = {
                navHostController.navigate(Destinations.SignIn.route)
            }) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(start = 14.dp, end = 6.dp)
                    .padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = "Person",
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Üye ol veya Giriş Yap",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                    Icon(
                        imageVector = trailingIcon,
                        contentDescription = "",
                        tint = Color.White
                    )
                }
            }
        }

    }
}

@Composable
fun AccountContact() {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        LoginOutlinedButton(
            "İşlem Rehberi / Yardım",
            Color.LightGray,
            borderStroke = BorderStroke(width = 1.dp, color = Color.LightGray)
        ) {
            //Todo onclick İşlem Rehberi / Yardım
        }
        LoginOutlinedButton(
            "Bize Ulaşın",
            Color.LightGray,
            borderStroke = BorderStroke(width = 1.dp, color = Color.LightGray)
        ) {
            //Todo onclick Bize Ulaşın
        }
        LoginOutlinedButton(
            "İletişim",
            Color.LightGray,
            borderStroke = BorderStroke(width = 1.dp, color = Color.LightGray)
        ) {
            //Todo onclick İletişim
        }

    }
}