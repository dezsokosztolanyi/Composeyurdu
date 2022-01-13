package com.kosztolanyi.composeyurdu

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.ExperimentalComposeUiApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kosztolanyi.composeyurdu.data.HomeViewModel
import com.kosztolanyi.composeyurdu.screens.main_screen.MainScreen
import com.kosztolanyi.composeyurdu.ui.theme.ComposeyurduTheme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalPermissionsApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@ExperimentalPagerApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel : HomeViewModel by viewModels()
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeyurduTheme {
                Surface(color = MaterialTheme.colors.background) {
                    MainScreen(viewModel = viewModel)
                }
            }
        }
    }
}

