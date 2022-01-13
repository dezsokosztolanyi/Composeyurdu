package com.kosztolanyi.composeyurdu.data.repositories

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase
import com.kosztolanyi.composeyurdu.models.Cart
import com.kosztolanyi.composeyurdu.models.Fav
import com.kosztolanyi.composeyurdu.models.HomeMainItem
import com.kosztolanyi.composeyurdu.models.mybook.BookDetail
import com.kosztolanyi.composeyurdu.models.mybook.Order
import com.kosztolanyi.composeyurdu.models.mybook.Price
import com.kosztolanyi.composeyurdu.models.mybook.Tag
import com.kosztolanyi.composeyurdu.util.Resource
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@ViewModelScoped
class FirestoreRepository @Inject constructor(private val firestore: FirebaseFirestore) {
    private val auth = Firebase.auth

    suspend fun getAllBooks(list : List<HomeMainItem>) : Resource<List<BookDetail>>{
        val bookList = arrayListOf<BookDetail>()
        try {
            list.forEach {
               firestore.collection("book")
                    .document(it.uid)
                    .get().await().toObject(BookDetail::class.java)?.let {
                       println("firestore item" + it.toString())
                        bookList.add(it)
                   }
            }
            println("bookLİST $bookList")
        }catch (e : java.lang.Exception){
            return Resource.Error(e.message)
        }
        return Resource.Success(bookList)
    }

    suspend fun deleteFavBook(favId: String) : Resource<Boolean>{
        try {
            firestore.collection("favs")
                .document(favId)
                .delete().await()
        }catch (e : java.lang.Exception){
            return Resource.Error(e.message)
        }
        return Resource.Success(true)
    }

    suspend fun setFavBook(fav: Fav) : Resource<Boolean>{
        try {
            firestore.collection("favs")
                .document(fav.favId)
                .set(fav)
                .await()
        }catch (e : Exception){
            return Resource.Error(e.message)
        }
        return Resource.Success(true)
    }

    suspend fun getFavBook(fav: Fav) : Resource<String?> {
        var response : String? = null
            try {
            response = firestore.collection("favs")
                .whereEqualTo("userId",fav.userId)
                .whereEqualTo("bookId", fav.bookId)
                .get()
                .await().documents?.first()?.getField<String>("favId") ?: null
        }catch (e : java.lang.Exception){
            return Resource.Error(e.message)
        }
        return Resource.Success(response)
    }

    suspend fun updateCartItem(cart: Cart) : Resource<Cart?>{
        try {
            firestore.collection("cart")
                .document(cart.cartId)
                .set(cart)
                .await()
        }
        catch (e : Exception){
            return Resource.Error(e.message)
        }
        return Resource.Success(cart)
    }

    suspend fun newCartItem(cart : Cart) : Resource<Cart?>{
        try {
       firestore.collection("cart")
            .document(cart.cartId)
            .set(cart)
            .await()
        }
        catch (e : Exception){
            return Resource.Error(e.message)
        }
        return Resource.Success(cart)
    }

    suspend fun isBookOnCart(bookId: String) : Resource<Cart?> {
        val response =  try {
            firestore.collection("cart")
                .whereEqualTo("userId", auth.currentUser?.uid)
                .whereEqualTo("bookId", bookId)
                .get().await().documents.first()?.toObject(Cart::class.java) ?: null
        } catch (e : Exception){
            return Resource.Error(e.message)
        }
        return Resource.Success(response)
    }


    suspend fun getBook(collection: String = "book", bookId: String): Resource<BookDetail> {
        val response = try {
            firestore.collection(collection)
                .whereEqualTo("bookId", bookId)
                .get()
                .await()
                .documents
                .map { documentSnapshot ->
                    documentSnapshot.toObject(BookDetail::class.java)
                }
        } catch (e: Exception) {
            return Resource.Error(e.message)
        }
        return Resource.Success(data = response.first()!!)
    }

    suspend fun getCart(collection: String = "cart"): Resource<List<Cart>> {
        val response = try {
            firestore.collection(collection)
                .whereEqualTo(
                    "userId",
                    auth.currentUser?.uid.toString()
                ).get()
                .await().documents.map { documentSnapshot ->
                    documentSnapshot.toObject(Cart::class.java)!!
                }
        } catch (e: Exception) {
            return Resource.Error(e.message)
        }
        return Resource.Success(data = response)
    }


    suspend fun setBooks() {
        getBookList().forEach { book ->
            firestore.collection("book")
                .document(book.bookId)
                .set(book)
                .addOnCompleteListener {
                    println(book.bookName + "tamamlandı.")
                }
        }
    }


    private fun getBookList(): List<BookDetail> {
        return listOf(
            BookDetail(
                bookAuthor = "Gabriel Garcia Marquez",
                bookId = "2",
                bookImageUrl = "https://img.kitapyurdu.com/v1/getImage/fn:11487152/wh:true/wi:220",
                bookName = "Yüzyılın Skandalı",
                bookPublisher = "CAN YAYINLARI",
                rate = 0,
                Order(false, 10, "24 Saatte Kargoda"),
                Price(currentPrice = "24.89", oldPrice = "39.50"),
                Tag(
                    ciltTipi = "Karton Kapak",
                    dil = "TÜRKÇE",
                    isbn = "9789750755798",
                    kagitCinsi = "Kitap Kağıdı",
                    size = "12.5 x 19.5 cm",
                    publishDate = "25.11.2021",
                    satilmaSayisi = 223,
                    sayfaSayisi = 376,
                    translator = "Emrah İmre"
                )
            ),
            BookDetail(
                bookAuthor = "Umberto Eco",
                bookId = "3",
                bookImageUrl = "https://img.kitapyurdu.com/v1/getImage/fn:11373991/wh:true/wi:220",
                bookName = "Gülün Adı",
                bookPublisher = "CAN YAYINLARI",
                rate = 5,
                Order(false, 10, "24 Saatte Kargoda"),
                Price(currentPrice = "30.27", oldPrice = "58.50"),
                Tag(
                    ciltTipi = "Karton Kapak",
                    dil = "TÜRKÇE",
                    isbn = "9789750732737",
                    kagitCinsi = "Kitap Kağıdı",
                    size = "12.5 x 19.5 cm",
                    publishDate = "12.09.2019",
                    satilmaSayisi = 30997,
                    sayfaSayisi = 732,
                    translator = "Şadan Karadeniz"
                )
            ),
            BookDetail(
                bookAuthor = "SAY YAYINLARI",
                bookId = "4",
                bookImageUrl = "https://img.kitapyurdu.com/v1/getImage/fn:254155/wh:true/wi:220",
                bookName = "Yeryüzünün Sosyal Fethi",
                bookPublisher = "SAY YAYINLARI",
                rate = 5,
                Order(false, 2, "24 Saatte Kargoda"),
                Price(currentPrice = "30.00", oldPrice = "40.00"),
                Tag(
                    ciltTipi = "Karton Kapak",
                    dil = "TÜRKÇE",
                    isbn = "9786050202830",
                    kagitCinsi = "Kitap Kağıdı",
                    size = "13.5 x 21 cm",
                    publishDate = "09.10.2013",
                    satilmaSayisi = 144,
                    sayfaSayisi = 400,
                    translator = "Gül Tonak"
                )
            ),
            BookDetail(
                bookAuthor = "James C. Scott",
                bookId = "5",
                bookImageUrl = "https://img.kitapyurdu.com/v1/getImage/fn:9540768/wh:true/wi:220",
                bookName = "Tahıla Karşı",
                bookPublisher = "KOÇ ÜNİVERSİTESİ YAYINLARI",
                rate = 5,
                Order(false, 10, "24 Saatte Kargoda"),
                Price(currentPrice = "20.80", oldPrice = "32.00"),
                Tag(
                    ciltTipi = "Karton Kapak",
                    dil = "TÜRKÇE",
                    isbn = "9786052116913",
                    kagitCinsi = "Kitap Kağıdı",
                    size = "13.5 x 20 cm",
                    publishDate = "23.08.2019",
                    satilmaSayisi = 2494,
                    sayfaSayisi = 272,
                    translator = ""
                )
            ),
            BookDetail(
                bookAuthor = "Simon Garfield",
                bookId = "6",
                bookImageUrl = "https://img.kitapyurdu.com/v1/getImage/fn:11295149/wh:true/wi:220",
                bookName = "Harita Üzerinde",
                bookPublisher = "DOMİNGO YAYINEVİ",
                rate = 0,
                Order(false, 5, "24 Saatte Kargoda"),
                Price(currentPrice = "32.89", oldPrice = "52.00"),
                Tag(
                    ciltTipi = "Karton Kapak",
                    dil = "TÜRKÇE",
                    isbn = "9786051981383",
                    kagitCinsi = "Kitap Kağıdı",
                    size = "14 x 22 cm",
                    publishDate = "21.10.2020",
                    satilmaSayisi = 1508,
                    sayfaSayisi = 456,
                    translator = ""
                )
            ),
            BookDetail(
                bookAuthor = "Herwig Wolfram",
                bookId = "7",
                bookImageUrl = "https://img.kitapyurdu.com/v1/getImage/fn:11263809/wh:true/wi:220",
                bookName = "Germenler",
                bookPublisher = "KRONİK KİTAP",
                rate = 0,
                Order(false, 10, "24 Saatte Kargoda"),
                Price(currentPrice = "48.46", oldPrice = "42.00"),
                Tag(
                    ciltTipi = "Karton Kapak",
                    dil = "TÜRKÇE",
                    isbn = "9786057635600",
                    kagitCinsi = "Kitap Kağıdı",
                    size = "13.5 x 21 cm",
                    publishDate = "24.08.2020",
                    satilmaSayisi = 818,
                    sayfaSayisi = 143,
                    translator = "Tuğba İsmailoğlu Kacır"
                )
            ),
            BookDetail(
                bookAuthor = "Barry Strauss",
                bookId = "8",
                bookImageUrl = "https://img.kitapyurdu.com/v1/getImage/fn:11484450/wh:true/wi:220",
                bookName = "Troya Savaşı",
                bookPublisher = "KRONİK KİTAP",
                rate = 5,
                Order(false, 10, "24 Saatte Kargoda"),
                Price(currentPrice = "65.00", oldPrice = "43.55"),
                Tag(
                    ciltTipi = "Karton Kapak",
                    dil = "TÜRKÇE",
                    isbn = "9786257631570",
                    kagitCinsi = "Kitap Kağıdı",
                    size = "13.5 x 21 cm",
                    publishDate = "18.11.2021",
                    satilmaSayisi = 157,
                    sayfaSayisi = 304,
                    translator = "Bahattin Bayram"
                )
            ),
            BookDetail(
                bookAuthor = "Hayrettin İhsan Erkoç",
                bookId = "9",
                bookImageUrl = "https://img.kitapyurdu.com/v1/getImage/fn:11484464/wh:true/wi:220",
                bookName = "Türkler",
                bookPublisher = "KRONİK KİTAP",
                rate = 5,
                Order(false, 10, "24 Saatte Kargoda"),
                Price(currentPrice = "23.45", oldPrice = "35.00"),
                Tag(
                    ciltTipi = "Karton Kapak",
                    dil = "TÜRKÇE",
                    isbn = "9786257631549",
                    kagitCinsi = "Kitap Kağıdı",
                    size = "13.5 x 21 cm",
                    publishDate = "18.11.2021",
                    satilmaSayisi = 206,
                    sayfaSayisi = 208,
                    translator = ""
                )
            ),
            BookDetail(
                bookAuthor = "Osman Karatay",
                bookId = "10",
                bookImageUrl = "https://img.kitapyurdu.com/v1/getImage/fn:11328353/wh:true/wi:220",
                bookName = "Ural-Altay Kuramı",
                bookPublisher = "SELENGE YAYINLARI",
                rate = 5,
                Order(false, 10, "24 Saatte Kargoda"),
                Price(currentPrice = "16.00", oldPrice = "20.00"),
                Tag(
                    ciltTipi = "Karton Kapak",
                    dil = "TÜRKÇE",
                    isbn = "9786054944668",
                    kagitCinsi = "Kitap Kağıdı",
                    size = "12.5 x 20.5 cm",
                    publishDate = "14.12.2020",
                    satilmaSayisi = 485,
                    sayfaSayisi = 128,
                    translator = ""
                )
            )
        )
    }

}