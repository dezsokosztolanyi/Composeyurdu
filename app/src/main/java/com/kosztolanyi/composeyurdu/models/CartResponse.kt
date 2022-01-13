package com.kosztolanyi.composeyurdu.models

import com.google.gson.annotations.SerializedName

data class CartResponse(
    @SerializedName("userId")
    val userId: String = "",
    @SerializedName("itemId")
    val itemId: String = "MOCK ITEM ID"
    ,@SerializedName("itemCount")
    val itemCount: Int = 1,
)
