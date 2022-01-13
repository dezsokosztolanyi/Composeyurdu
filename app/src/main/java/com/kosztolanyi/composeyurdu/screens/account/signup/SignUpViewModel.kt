package com.kosztolanyi.composeyurdu.screens.account.signup

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kosztolanyi.composeyurdu.models.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : ViewModel() {
    val isLoading = mutableStateOf(false)

    val name: MutableState<String> = mutableStateOf("")
    val surname: MutableState<String> = mutableStateOf("")
    val mailAddress: MutableState<String> = mutableStateOf("")
    val password: MutableState<String> = mutableStateOf("")
    val password2: MutableState<String> = mutableStateOf("")

    val checkPolicy: MutableState<Boolean> = mutableStateOf(false)
    val checkCampaign: MutableState<Boolean> = mutableStateOf(false)


//    fun createAnUser(mail : String, password : String){
//        viewModelScope.launch {
//            fireStoreRepository.createAnUser(mail = mail, password = password)
//        }
//    }

    fun createAnUser(mail: String, password: String){
        isLoading.value = true
        viewModelScope.launch {
            auth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = task.result?.user?.uid.toString()
                    val user = User(
                        name.value,
                        surname.value,
                        mailAddress.value,
                        password2.value
                    )

                    firestore.collection("user")
                        .document(uid)
                        .set(user)
                        .addOnCompleteListener {
                            isLoading.value = false
                        }
                        .addOnFailureListener {
                            isLoading.value = false
                        }
                }else{
                    isLoading.value = false
                }
            }
        }
    }


    fun areValuesValid(): Boolean {
        if (checkPolicy.value && name.value.isNotEmpty() && surname.value.isNotEmpty() && mailAddress.value.isNotEmpty() && password.value.equals(
                password2.value
            )
        ) {
            return true
        } else {
            return false
        }
    }
}