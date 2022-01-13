package com.kosztolanyi.composeyurdu.models

import com.google.gson.annotations.SerializedName

data class StaticBanner(
    @SerializedName("bannerId")
    val bannerId: String,
    @SerializedName("bannerImageUrl")
    val bannerImageUrl: String
)