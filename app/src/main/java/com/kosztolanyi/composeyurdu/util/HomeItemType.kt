package com.kosztolanyi.composeyurdu.util

enum class HomeItemType(val listName : String,val topic : String,val seeAll : String,val argumentName : String) {
    POPULAR("popular",topic = "İlgi Görenler", seeAll ="Tümü",argumentName = "ilgigorenler"),
    NEW("news",topic = "En Çok Satanlar", seeAll = "Tümü",argumentName = "encoksatanlar"),
    BESTSELLER("bestseller",topic = "Yeni Çıkanlar", seeAll ="Tümü",argumentName = "yenicikanlar"),
    AUTHOR("author",topic = "Yazarlar", seeAll ="Tümü",argumentName = "yazarlar"),
    PUBLISHER("publisher",topic = "Yayınevleri", seeAll ="Tümü",argumentName = "yayinevleri")
}