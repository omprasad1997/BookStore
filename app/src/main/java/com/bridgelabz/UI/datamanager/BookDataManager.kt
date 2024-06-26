package com.bridgelabz.UI.datamanager

import android.content.Context
import android.util.Log
import com.bridgelabz.HelperClass.SharedPreferenceHelper
import com.bridgelabz.UI.model.BookModel
import com.bridgelabz.UI.model.responsemodel.BookResponseModel
import com.bridgelabz.UI.model.CartModel
import com.bridgelabz.UI.model.UserModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.IOException
import java.nio.charset.Charset


class BookDataManager(private val context: Context?) {
    val TAG = "BookDataManager"

    fun getBookList(): List<BookModel> {
        val jsonString = getJSONFromAssets()!!
        val booksListType = object : TypeToken<ArrayList<BookResponseModel>>() {}.type

        val user: UserModel? = getLoggedIn()
        val booksObj: ArrayList<BookResponseModel> = Gson().fromJson(jsonString, booksListType)
        Log.e(TAG, "bookObj: $booksObj")

        val favouriteBookList = user?.favouriteBookList
        val cartBookIdsList = user?.cartBookList?.map { it.bookId }

        Log.e(TAG, "print: $user")

        return booksObj.map {
            BookModel(it).apply {
                isFavourite = favouriteBookList?.contains(it.bookId) ?: false
                isCarted = cartBookIdsList?.contains(it.bookId) ?: false
            }
        }
    }

    fun getCartItemBooks(): ArrayList<CartModel> {
        val loggedInUser = getLoggedIn() ?: return ArrayList()
        val cartItemBooks: ArrayList<CartModel> = ArrayList()
        val cartResponses = loggedInUser.cartBookList
        val books = getBookList()
        val bookIds: List<Int> = books.map {
            it.bookId
        }
        cartResponses.forEachIndexed { index, cartResponseModel ->
            if (bookIds.contains(cartResponseModel.bookId)) {
                val indexOfBookId = bookIds.indexOf(cartResponseModel.bookId)
                val element = CartModel(cartResponseModel.quantityOfBook, books[indexOfBookId])
                cartItemBooks.add(element)
            }
        }
        return cartItemBooks
    }

    private fun getLoggedIn(): UserModel? {
        val sharedPreferenceHelper = SharedPreferenceHelper(context!!)
        val path = context.filesDir.absolutePath
        val file = File(path, "use_credential.json")
        val booksListType = object : TypeToken<Array<UserModel>>() {}.type
        val bookList: Array<UserModel> = Gson().fromJson(file.readText(), booksListType)
        Log.e(TAG, "Inside getLoggedIn: bookList")
        return bookList.findLast {
            (sharedPreferenceHelper.getLoggedInUserId() == it.id)
        }
    }

    private fun getJSONFromAssets(): String? {

        val json: String?
        val charset: Charset = Charsets.UTF_8
        try {
            val myUsersJSONFile = context?.assets?.open("Books.json")
            val size = myUsersJSONFile?.available()
            val buffer = size?.let { ByteArray(it) }
            myUsersJSONFile!!.read(buffer)
            myUsersJSONFile.close()
            json = buffer?.let { String(it, charset) }
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }

    fun getTotalPrice(cartItemList: java.util.ArrayList<CartModel>): Double {
        var totalPrice = 0.0
        for (i in 0 until cartItemList.size) {
            val cartBookQuantity = cartItemList[i].bookQuantity
            val cartBookPrice = cartItemList[i].book.discountedPrice!!.toDouble()
            totalPrice += (cartBookQuantity * cartBookPrice)
        }
        return totalPrice
    }

}

