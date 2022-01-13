package com.kosztolanyi.composeyurdu.models.mybook


import com.google.gson.annotations.SerializedName

data class Price(
    @SerializedName("currentPrice")
    val currentPrice: String = "0.0",
    @SerializedName("oldPrice")
    val oldPrice: String = "0.0"
)