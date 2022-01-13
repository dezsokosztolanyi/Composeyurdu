package com.kosztolanyi.composeyurdu.screens.search

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor() : ViewModel() {

    val searchQuery : MutableState<String> = mutableStateOf("")
}