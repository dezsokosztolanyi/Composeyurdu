package com.kosztolanyi.composeyurdu.models

import com.google.gson.annotations.SerializedName

data class Campaign(
    @SerializedName("campaignId")
    val campaignId: String,
    @SerializedName("campaignImageUrl")
    val campaignImageUrl: String
)
