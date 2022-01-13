package com.kosztolanyi.composeyurdu.models

data class CartPriceItem(
    val itemCount : Int = 0,
    val amount : Double = 0.0,
    val bonusPoints : Int = 0
)
