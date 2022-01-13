package com.kosztolanyi.composeyurdu.models.mybook


import com.google.gson.annotations.SerializedName

data class Tag(
    @SerializedName("ciltTipi")
    val ciltTipi: String = "",
    @SerializedName("dil")
    val dil: String = "",
    @SerializedName("isbn")
    val isbn: String = "",
    @SerializedName("kagitCinsi")
    val kagitCinsi: String = "",
    @SerializedName("size")
    val size: String = "",
    @SerializedName("publishDate")
    val publishDate: String = "",
    @SerializedName("satilmaSayisi")
    val satilmaSayisi: Int = 0,
    @SerializedName("sayfaSayisi")
    val sayfaSayisi: Int = 0,
    @SerializedName("translator")
    val translator: String? = null
)