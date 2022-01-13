package com.kosztolanyi.composeyurdu.screens.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kosztolanyi.composeyurdu.models.Cart
import com.kosztolanyi.composeyurdu.models.CartPriceItem
import com.kosztolanyi.composeyurdu.data.repositories.FirestoreRepository
import com.kosztolanyi.composeyurdu.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class CartViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestoreRepository: FirestoreRepository
) : ViewModel() {

    private val _cartResponse: MutableStateFlow<Resource<List<Cart>>> =
        MutableStateFlow(Resource.Loading())
    val cartResponse: StateFlow<Resource<List<Cart>>> get() = _cartResponse

    fun isUserOnline() : String?{
        return auth.currentUser?.uid
    }


    init {
        getCart()
    }

    fun getCart() {
        viewModelScope.launch(Dispatchers.IO) {
            when(val response = firestoreRepository.getCart()){
                is Resource.Success -> {
                    response.data.let {
//                        println("get cart $it")
                        _cartResponse.value = Resource.Success(it!!)
                    }
                }
                is Resource.Error -> {
                    println("error")
                }
                is Resource.Loading -> {
                    println("loading")
                }
            }

        }
    }

    fun calculator(carts : List<Cart>) : CartPriceItem {
        var itemCount : Int  = 0
        var amount : Double = 0.0
        var bonusPoints : Int = 0

        carts.forEach { cart ->
            println(cart.supplyTime.toString())
            amount += cart.currentPrice.toDouble() * cart.itemCount
            itemCount += cart.itemCount
        }

        bonusPoints = amount.roundToInt().times(2)

        return CartPriceItem(itemCount = itemCount, amount = amount, bonusPoints = bonusPoints)
    }


}