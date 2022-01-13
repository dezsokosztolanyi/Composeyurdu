package com.kosztolanyi.composeyurdu.models.mybook


import com.google.gson.annotations.SerializedName

data class Order(
    @SerializedName("preOrder")
    val preOrder: Boolean = false,
    @SerializedName("stockAmount")
    val stockAmount: Int = 0,
    @SerializedName("supplyTime")
    val supplyTime: String = ""
)