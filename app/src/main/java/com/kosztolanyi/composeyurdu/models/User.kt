package com.kosztolanyi.composeyurdu.models

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("username")
    val username: String = "",
    @SerializedName("lastname")
    val lastname: String = "",
    @SerializedName("emailAddress")
    val emailAddress: String = "",
    @SerializedName("password")
    val password: String = ""
)
