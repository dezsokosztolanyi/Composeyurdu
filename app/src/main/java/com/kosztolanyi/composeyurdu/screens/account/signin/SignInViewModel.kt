package com.kosztolanyi.composeyurdu.screens.account.signin

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val auth : FirebaseAuth) : ViewModel() {
    val signInState : MutableState<SignInState> = mutableStateOf(SignInState.IDLE)

    val mailAddress: MutableState<String> = mutableStateOf("")
    val password: MutableState<String> = mutableStateOf("")

    val forgotPassword : MutableState<String> = mutableStateOf("")

    fun signIn(mail : String = "asd@gmail.com", password : String = "123456"){
        signInState.value = SignInState.LOADING
        auth.signInWithEmailAndPassword(mail,password)
            .addOnSuccessListener {
                if (it.user != null){
                    signInState.value = SignInState.SUCCESS
                }else{
                    signInState.value = SignInState.ERROR
                }
            }
            .addOnFailureListener {
                signInState.value = SignInState.ERROR
            }
    }
}