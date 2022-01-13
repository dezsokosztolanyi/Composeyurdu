package com.kosztolanyi.composeyurdu.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.kosztolanyi.composeyurdu.components.bottombar.BottomBarScreen
import com.kosztolanyi.composeyurdu.data.HomeViewModel
import com.kosztolanyi.composeyurdu.screens.account.account.AccountScreen
import com.kosztolanyi.composeyurdu.screens.account.account.AccountViewModel
import com.kosztolanyi.composeyurdu.screens.cart.CartScreen
import com.kosztolanyi.composeyurdu.screens.categories.CategoriesScreen
import com.kosztolanyi.composeyurdu.screens.search.SearchScreen
import com.kosztolanyi.composeyurdu.screens.search.SearchViewModel
import com.kosztolanyi.composeyurdu.screens.account.signin.SignInScreen
import com.kosztolanyi.composeyurdu.screens.account.signin.SignInViewModel
import com.kosztolanyi.composeyurdu.screens.account.signup.SignUpScreen
import com.kosztolanyi.composeyurdu.screens.account.signup.SignUpViewModel
import com.kosztolanyi.composeyurdu.screens.campaign.CampaignScreen
import com.kosztolanyi.composeyurdu.screens.cart.CartViewModel
import com.kosztolanyi.composeyurdu.screens.detail.DetailScreen
import com.kosztolanyi.composeyurdu.screens.home.HomeScreen
import com.kosztolanyi.composeyurdu.screens.payment.PaymentScreen
import com.kosztolanyi.composeyurdu.screens.search.barcode.BarcodeScanScreen
import com.kosztolanyi.composeyurdu.screens.seeall.SeeAllScreen
import com.kosztolanyi.composeyurdu.ui.theme.kitapyurduOrange
import com.kosztolanyi.composeyurdu.ui.theme.kitapyurduGray
import com.kosztolanyi.composeyurdu.util.HomeItemType

@ExperimentalPermissionsApi
@ExperimentalAnimationApi
@ExperimentalComposeUiApi
@ExperimentalPagerApi
@Composable
fun NavGraph(navHostController: NavHostController, viewModel: HomeViewModel) {
    val systemUiController = rememberSystemUiController()
    NavHost(navController = navHostController, startDestination = BottomBarScreen.Home.route) {
        composable(route = BottomBarScreen.Home.route) {
            systemUiController.setStatusBarColor(kitapyurduGray)
//            systemUiController.setNavigationBarColor(MediumPriorityColor)
            HomeScreen(viewModel, navHostController)
        }
        composable(route = BottomBarScreen.Campaigns.route) {
//            AccountScreen(navHostController = navHostController)
            systemUiController.setStatusBarColor(kitapyurduOrange)
            CampaignScreen(viewModel = viewModel)
        }
        composable(route = BottomBarScreen.Categories.route) {
            systemUiController.setStatusBarColor(kitapyurduOrange)
            CategoriesScreen()
        }
        composable(route = BottomBarScreen.Search.route) {
            val viewModel: SearchViewModel = viewModel()
            systemUiController.setStatusBarColor(kitapyurduOrange)
            SearchScreen(viewModel = viewModel, navHostController = navHostController)
        }
        composable(route = BottomBarScreen.Cart.route) {
            systemUiController.setStatusBarColor(kitapyurduOrange)
            val viewModel = hiltViewModel<CartViewModel>()
            CartScreen(viewModel = viewModel, navHostController = navHostController)
        }
        composable(route = Destinations.SignIn.route) {
            val viewModel = hiltViewModel<SignInViewModel>()
            SignInScreen(viewModel = viewModel, navHostController = navHostController)
        }
        composable(route = Destinations.SignUp.route) {
            val viewModel = hiltViewModel<SignUpViewModel>()
            SignUpScreen(viewModel = viewModel, navHostController = navHostController)
        }
        composable(
            route = Destinations.Detail.route + "/{bookId}",
            arguments = listOf(navArgument("bookId") {
                type = NavType.StringType
            })
        ) { navBackStackEntry ->
            navBackStackEntry.arguments?.getString("bookId")?.let { bookId ->
                systemUiController.setStatusBarColor(kitapyurduGray)
                viewModel.getSelectedBook(bookId)
                DetailScreen(
                    viewModel = viewModel,
                    navHostController = navHostController,
                    bookId = bookId
                )
            }
        }
        composable(route = Destinations.Account.route) {
            val viewModel = hiltViewModel<AccountViewModel>()
            systemUiController.setStatusBarColor(kitapyurduOrange)
            AccountScreen(viewModel = viewModel, navHostController = navHostController)
        }
        composable(route = Destinations.Payment.route) {
            PaymentScreen(navHostController = navHostController)
        }
        composable(route = Destinations.Barcode.route) {
            BarcodeScanScreen(navHostController = navHostController)
        }
        composable(route = Destinations.SeeAll.route + "/{title}", arguments = listOf(navArgument("title") {
            type = NavType.StringType
        })) { backStackEntry ->
            val arg = backStackEntry.arguments?.getString("title")
            arg?.let { argument ->
                when (argument) {
                    HomeItemType.POPULAR.argumentName -> {
                        val type = HomeItemType.POPULAR
                        viewModel.seeAllItems(type)
                        SeeAllScreen(
                            type = type,
                            navHostController = navHostController,
                            homeViewModel = viewModel
                        )
                    }
                    HomeItemType.BESTSELLER.argumentName -> {
                        val type = HomeItemType.BESTSELLER
                        viewModel.seeAllItems(type)
                        SeeAllScreen(
                            type = type,
                            navHostController = navHostController,
                            homeViewModel = viewModel
                        )
                    }
                    HomeItemType.NEW.argumentName -> {
                        val type = HomeItemType.NEW
                        viewModel.seeAllItems(type)
                        SeeAllScreen(
                            type = type,
                            navHostController = navHostController,
                            homeViewModel = viewModel
                        )
                    }
                    else -> {
                        val type = HomeItemType.POPULAR
                        viewModel.seeAllItems(type)
                        SeeAllScreen(
                            type = type,
                            navHostController = navHostController,
                            homeViewModel = viewModel
                        )
                    }
                }
            }
        }
    }
}



