package com.kosztolanyi.composeyurdu.models

import com.google.gson.annotations.SerializedName

data class Banner(
    @SerializedName("bannerId")
    val bannerId: String,
    @SerializedName("bannerImageUrl")
    val bannerImageUrl: String
)
