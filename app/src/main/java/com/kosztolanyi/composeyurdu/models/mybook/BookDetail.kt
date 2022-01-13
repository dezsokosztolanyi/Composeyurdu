package com.kosztolanyi.composeyurdu.models.mybook


import com.google.gson.annotations.SerializedName

data class BookDetail(
    @SerializedName("bookAuthor")
    val bookAuthor: String = "",
    @SerializedName("bookId")
    val bookId: String= "",
    @SerializedName("bookImageUrl")
    val bookImageUrl: String= "",
    @SerializedName("bookName")
    val bookName: String= "",
    @SerializedName("bookPublisher")
    val bookPublisher: String= "",
    @SerializedName("rate")
    val rate: Int = 0,
    @SerializedName("order")
    val order: Order = Order(),
    @SerializedName("price")
    val price: Price = Price(),
    @SerializedName("tag")
    val tag: Tag = Tag()
)