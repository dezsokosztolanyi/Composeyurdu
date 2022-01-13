package com.kosztolanyi.composeyurdu.models

import com.google.gson.annotations.SerializedName

data class Publishers(
    @SerializedName("publisherId")
    val publisherId: String,
    @SerializedName("publisherImageUrl")
    val publisherImageUrl: String
)
