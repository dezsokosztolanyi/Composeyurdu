package com.kosztolanyi.composeyurdu.models

import com.google.gson.annotations.SerializedName

data class Author(
    @SerializedName("authorId")
    val authorId: String,
    @SerializedName("authorName")
    val authorName: String,
    @SerializedName("authorImageUrl")
    val authorImageUrl: String
)
