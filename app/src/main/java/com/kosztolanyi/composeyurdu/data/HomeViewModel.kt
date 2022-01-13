package com.kosztolanyi.composeyurdu.data

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.kosztolanyi.composeyurdu.data.repositories.FirestoreRepository
import com.kosztolanyi.composeyurdu.models.*
import com.kosztolanyi.composeyurdu.models.mybook.BookDetail
import com.kosztolanyi.composeyurdu.models.mybook.Order
import com.kosztolanyi.composeyurdu.models.mybook.Price
import com.kosztolanyi.composeyurdu.models.mybook.Tag
import com.kosztolanyi.composeyurdu.repository.ComposeyurduRepository
import com.kosztolanyi.composeyurdu.screens.detail.Like
import com.kosztolanyi.composeyurdu.util.HomeItemType
import com.kosztolanyi.composeyurdu.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ComposeyurduRepository,
    private val firestoreRepository: FirestoreRepository,
    private val auth : FirebaseAuth
) :
    ViewModel() {


    var homeDataItem by mutableStateOf(
        HomeDataItem(
            emptyList(),
            emptyList(),
            emptyList(),
            emptyList(),
            emptyList(),
            emptyList(),
            emptyList(),
            emptyList()
        )
    )
    var errorMessage by mutableStateOf("")
    var isLoading by mutableStateOf(false)


    init {
        getHomeData()
    }


    private fun getHomeData() {
        viewModelScope.launch {
            isLoading = true

            when (val result = repository.getHomeData()) {

                is Resource.Success -> {
                    homeDataItem = result.data!!.first()
                    errorMessage = ""
                    isLoading = false
                    getDetailBanners()

                    println(homeDataItem.toString())
                }

                is Resource.Error -> {
                    errorMessage = result.message.toString()
                    isLoading = false
                }
            }
        }
    }


    fun convertNewsToHomeMainItem(): List<HomeMainItem> {
        return homeDataItem.news.map {
            HomeMainItem(uid = it.bookId, url = it.bookImageUrl)
        }
    }

    fun convertPopularToHomeMainItem(): List<HomeMainItem> {
        return homeDataItem.popular.map {
            HomeMainItem(uid = it.bookId, url = it.bookImageUrl)
        }
    }

    fun convertBestsellerToHomeMainItem(): List<HomeMainItem> {
        return homeDataItem.bestseller.map {
            HomeMainItem(uid = it.bookId, url = it.bookImageUrl)
        }
    }

    fun convertAuthorToHomeMainItem(): List<HomeMainItem> {
        return homeDataItem.authors.map {
            HomeMainItem(uid = it.authorId, name = it.authorName, url = it.authorImageUrl)
        }
    }

    fun convertPublishersToHomeMainItem(): List<HomeMainItem> {
        return homeDataItem.publishers.map {
            HomeMainItem(uid = it.publisherId, url = it.publisherImageUrl)
        }
    }

                                      /////DETAİL SCREEN////////
    //////////////--------------------------------------------------------------------//////////////////
    var state by mutableStateOf(MutableTransitionState(Like.Gone))

    private val _selectedBook: MutableStateFlow<Resource<BookDetail>> =
        MutableStateFlow(Resource.Loading())
    val selectedBook: StateFlow<Resource<BookDetail>> get() = _selectedBook

    private val _selectedBookCartInfo: MutableStateFlow<Resource<Cart?>> =
        MutableStateFlow(Resource.Loading())
    val selectedBookCartInfo: StateFlow<Resource<Cart?>> get() = _selectedBookCartInfo

    val get3RandomStaticBanners = mutableStateOf(arrayListOf<StaticBanner>())

    private val _bookFav: MutableStateFlow<Resource<String?>> =
        MutableStateFlow(Resource.Loading())
    val bookFav: StateFlow<Resource<String?>> get() = _bookFav

    fun deleteFavBook(favId : String){
        viewModelScope.launch(Dispatchers.IO) {
            firestoreRepository.deleteFavBook(favId).also {
                getFavBook()
            }
        }
    }


    fun setFavBook(fav: Fav = Fav(UUID.randomUUID().toString(),auth.uid.toString(),selectedBook.value.data!!.bookId)){
        viewModelScope.launch(Dispatchers.IO) {
           firestoreRepository.setFavBook(fav).also {
               getFavBook(fav)
           }
        }
    }

    fun getFavBook(fav: Fav = Fav(userId = auth.uid.toString(), bookId = selectedBook.value.data!!.bookId)){
        viewModelScope.launch(Dispatchers.IO) {
           _bookFav.emit(firestoreRepository.getFavBook(fav))
        }
    }

    fun isUserOnline() : String?{
        return auth.currentUser?.uid
    }

    fun newCartItem(cart: Cart){
        viewModelScope.launch(Dispatchers.IO) {
            _selectedBookCartInfo.value = firestoreRepository.newCartItem(cart)
        }
    }

    fun updateCartItem(cart: Cart){
        viewModelScope.launch(Dispatchers.IO) {
            _selectedBookCartInfo.value = firestoreRepository.updateCartItem(cart = cart)
        }
    }

    fun getDetailBanners(){
        get3RandomStaticBanners.value = arrayListOf()
        if (!homeDataItem.staticBanner.isNullOrEmpty()){
            for (i in 0..2){
                println(i.toString())
                get3RandomStaticBanners.value.add(homeDataItem.staticBanner.random())
            }
        }
    }


    fun getSelectedBookCartInfo(bookId : String){
        viewModelScope.launch(Dispatchers.IO) {
            when(val response = firestoreRepository.isBookOnCart(bookId)){
                is Resource.Success -> {
                    if (response.data?.cartId != null){
                        _selectedBookCartInfo.value = Resource.Success(response.data)
                    }else{
                        _selectedBookCartInfo.value = Resource.Success(null)
                    }
                }
                is Resource.Error -> {
                    println("selectedBookCartInfo Erroe")
                    _selectedBookCartInfo.value = Resource.Success(null)
                }
                is Resource.Loading -> {
                    _selectedBookCartInfo.value = Resource.Loading()
                }
            }
        }
    }


    fun getSelectedBook(bookId : String){
        viewModelScope.launch {

            when(val response = firestoreRepository.getBook(bookId = bookId)){
                is Resource.Success -> {
                    response.data.let {
                        _selectedBook.value = Resource.Success(it!!)
                    }
                }
                is Resource.Error -> {
                    println("error")
                }
                is Resource.Loading -> {
                    println("loading")
                }
            }
    }
   }


    fun getBookDetail(): BookDetail {
        return BookDetail(
            bookAuthor = "Kevin J. Hayes",
            bookId = "1",
            bookImageUrl = "https://img.kitapyurdu.com/v1/getImage/fn:11249514/wh:true/wi:220",
            bookName = "Herman Melville",
            bookPublisher = "RUNİK KİTAP",
            rate = 4,
            Order(false, 10, "24 Saatte Kargoda"),
            Price("34.40", "43.00"),
            Tag(
                ciltTipi = "Karton Kapak",
                "TÜRKÇE",
                "92149124912",
                "Kitap Kağıdı",
                "13.5 x 20.5 cm",
                "09.12.2021",
                22,
                197,
                "Arlet İncidüzen"
            )
        )
    }

    /////////////////////////////////////////SEE ALL////////////////////////////////////////////
    private val _seeAllItems: MutableStateFlow<Resource<List<BookDetail>>> =
        MutableStateFlow(Resource.Loading())
    val seeAllItems: StateFlow<Resource<List<BookDetail>>> get() = _seeAllItems

    fun seeAllItems(type: HomeItemType){
        viewModelScope.launch(Dispatchers.IO) {
        when(type){
            HomeItemType.NEW ->{
                _seeAllItems.value = Resource.Loading()
                _seeAllItems.emit(firestoreRepository.getAllBooks(convertNewsToHomeMainItem()))
            }
            HomeItemType.POPULAR ->{
                _seeAllItems.value = Resource.Loading()
                _seeAllItems.emit(firestoreRepository.getAllBooks(convertPopularToHomeMainItem()))
            }
            HomeItemType.BESTSELLER ->{
                _seeAllItems.value = Resource.Loading()
                _seeAllItems.emit(firestoreRepository.getAllBooks(convertBestsellerToHomeMainItem()))
            }
            else ->{
                _seeAllItems.value = Resource.Loading()
                _seeAllItems.emit(firestoreRepository.getAllBooks(convertNewsToHomeMainItem()))
            }
        }
        }
    }

}

