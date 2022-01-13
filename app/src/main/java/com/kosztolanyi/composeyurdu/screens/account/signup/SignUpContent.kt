package com.kosztolanyi.composeyurdu.screens.account.signup

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.kosztolanyi.composeyurdu.components.bottombar.BottomBarScreen
import com.kosztolanyi.composeyurdu.components.button.KitapyurduLargeButton
import com.kosztolanyi.composeyurdu.ui.theme.kitapyurduOrange

@Composable
fun SignUpContent(viewModel: SignUpViewModel, navHostController: NavHostController) {
    val scrollState = rememberScrollState()
    val isLoading = viewModel.isLoading.value
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Center){
        if (isLoading){
            CircularProgressIndicator()
        }
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(bottom = 60.dp)
                .padding(horizontal = 14.dp)
                .padding(top = 18.dp)) {
            SignUpFieldItem(fieldName = "Ad: *", KeyboardType.Text, fieldText = viewModel.name.value) {
                viewModel.name.value = it
            }
            SignUpFieldItem(fieldName = "Soyad: *", KeyboardType.Text, fieldText = viewModel.surname.value) {
                viewModel.surname.value = it
            }
            SignUpFieldItem(fieldName = "E-Posta Adresi: *", KeyboardType.Email,fieldText =  viewModel.mailAddress.value) {
                viewModel.mailAddress.value = it
            }
            SignUpFieldItem(fieldName = "Şifre: *", KeyboardType.Password,PasswordVisualTransformation(),fieldText =  viewModel.password.value) {
                viewModel.password.value = it
            }
            SignUpFieldItem(
                fieldName = "Şifre Tekrarı: *",
                KeyboardType.Password,
                PasswordVisualTransformation(),
                fieldText = viewModel.password2.value
            ) {
                viewModel.password2.value = it
            }
            Spacer(modifier = Modifier.height(12.dp))
            CheckableItem(
                annotatedString = getPolicyText(),
                onChecked = viewModel.checkPolicy.value,
                onCheckedChanged = {
                    viewModel.checkPolicy.value = it
                })
            CheckableItem(
                annotatedString = getCampaignText(),
                onChecked = viewModel.checkCampaign.value,
                onCheckedChanged = {
                    viewModel.checkCampaign.value = it
                })
            KitapyurduLargeButton(text = "Üye Ol") {
                //Todo onButtonClicked
                viewModel.createAnUser(viewModel.mailAddress.value,viewModel.password.value).also {
                    if (!isLoading){
                        Toast.makeText(context,"${viewModel.name.value},\naramıza katılmana çok memnun olduk!",Toast.LENGTH_SHORT).show()
                        navHostController.navigate(BottomBarScreen.Home.route)
                    }
                }
//            navHostController.navigate(BottomBarScreen.Home.route)
            }

        }
    }
}


@Composable
fun SignUpFieldItem(
    fieldName: String,
    keyboardType: KeyboardType,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    fieldText: String,
    onFieldTextChanged: (String) -> Unit
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)) {
        Text(text = fieldName, color = Color.DarkGray)
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = fieldText,
            onValueChange = {
                onFieldTextChanged(it)
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = ImeAction.Next
            ),
            visualTransformation = visualTransformation,
            trailingIcon = {
                if (fieldText.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Rounded.Cancel,
                        contentDescription = "Delete",
                        modifier = Modifier.clickable {
                            onFieldTextChanged("")
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

@Composable
fun CheckableItem(
    annotatedString: AnnotatedString,
    onChecked: Boolean,
    onCheckedChanged: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(0.1f)
                .align(CenterVertically)
        ) {
            Checkbox(
                colors = CheckboxDefaults.colors(
                    checkedColor = kitapyurduOrange,
                    checkmarkColor = Color.White
                ), checked = onChecked, onCheckedChange = {
                    onCheckedChanged(it)
                })
        }
        Box(
            modifier = Modifier
                .weight(1.2f)
                .align(CenterVertically)
                .padding(start = 8.dp)
        ) {
            Text(annotatedString)
        }
    }
}

fun getPolicyText(): AnnotatedString {
    return buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = kitapyurduOrange,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            )
        ) {
            append("Gizlilik ve Güvenlik Politikası")
        }
        withStyle(
            style = SpanStyle(
                color = Color.DarkGray,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            )
        ) {
            append("'nı okudum onaylıyorum.")
        }
    }
}

fun getCampaignText(): AnnotatedString {
    return buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = Color.DarkGray,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            )
        ) {
            append("Kampayanlardan haberdar olmak için ")
        }
        withStyle(
            style = SpanStyle(
                color = kitapyurduOrange,
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            )
        ) {
            append("elektronik ileti")
        }
        withStyle(
            style = SpanStyle(
                color = Color.DarkGray,
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal
            )
        ) {
            append(" almak istiyorum.")
        }
    }
}