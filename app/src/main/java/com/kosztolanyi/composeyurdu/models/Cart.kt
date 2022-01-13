package com.kosztolanyi.composeyurdu.models

import com.google.gson.annotations.SerializedName

data class Cart(
    @SerializedName("cartId")
    val cartId: String= "",
    @SerializedName("userId")
    val userId: String= "",
    @SerializedName("bookId")
    val bookId: String= "",
    @SerializedName("bookImageUrl")
    val bookImageUrl: String= "",
    @SerializedName("bookName")
    val bookName: String= "",
    @SerializedName("supplyTime")
    val supplyTime: String = "",
    @SerializedName("currentPrice")
    val currentPrice: String = "0.0",
    @SerializedName("itemCount")
    val itemCount : Int = 1
)
