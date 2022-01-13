package com.kosztolanyi.composeyurdu.screens.detail

import kotlin.math.roundToInt


fun CalculateDiscount(currentPrice : Double, oldPrice : Double) : String{
    val oneHundred = 100
    val count = 34.60
    val totalCount = 43.00

    val percentage = (currentPrice / oldPrice) * 100

    return (100 - percentage.roundToInt()).toString()
}