package com.kosztolanyi.composeyurdu.models


import com.google.gson.annotations.SerializedName

data class Popular(
    @SerializedName("bookId")
    val bookId: String,
    @SerializedName("bookImageUrl")
    val bookImageUrl: String
)