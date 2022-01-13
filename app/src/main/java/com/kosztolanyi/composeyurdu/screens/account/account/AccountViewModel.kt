package com.kosztolanyi.composeyurdu.screens.account.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kosztolanyi.composeyurdu.models.User
import com.kosztolanyi.composeyurdu.data.repositories.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val accountRepository: AccountRepository
    ) : ViewModel() {
    var user : User? by mutableStateOf(null)
    var loadingState by mutableStateOf(false)

    fun isUserOnline(){
        loadingState = true
        viewModelScope.launch(Dispatchers.IO) {
            user = accountRepository.isUserSignedIn()
            loadingState = false
            println("isUserOnline ${user?.emailAddress}")
        }
    }

    fun exitAccount(){
        accountRepository.exitAccount().also {
            user = null
        }
    }
}