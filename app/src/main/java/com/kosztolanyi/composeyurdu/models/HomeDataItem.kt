package com.kosztolanyi.composeyurdu.models


import com.google.gson.annotations.SerializedName

data class HomeDataItem(
    @SerializedName("authors")
    val authors: List<Author>,
    @SerializedName("bestseller")
    val bestseller: List<Bestseller>,
    @SerializedName("news")
    val news: List<New>,
    @SerializedName("popular")
    val popular: List<Popular>,
    @SerializedName("banner")
    val banner: List<Banner>,
    @SerializedName("staticBanner")
    val staticBanner: List<StaticBanner>,
    @SerializedName("publishers")
    val publishers: List<Publishers>,
    @SerializedName("campaign")
    val campaigns: List<Campaign>
)