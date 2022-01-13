package com.kosztolanyi.composeyurdu.models

import com.google.gson.annotations.SerializedName

data class Random(
    @SerializedName("bookId")
    val bookId : String = ""
)
