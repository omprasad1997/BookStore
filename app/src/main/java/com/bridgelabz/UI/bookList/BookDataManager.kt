package com.bridgelabz.UI.bookList

import android.content.Context
import android.util.Log
import com.bridgelabz.HelperClass.SharedPreferenceHelper
import com.bridgelabz.UI.model.BookModel
import com.bridgelabz.UI.model.BookResponseModel
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

        Log.e(TAG, "print: $user")

        return booksObj.map {
            BookModel(it).apply {
                isFavourite = favouriteBookList!!.contains(it.bookId)
            }
        }
    }

    private fun getCartBookList(): List<BookModel> {
        val jsonString = getJSONFromAssets()!!
        val booksListType = object : TypeToken<ArrayList<BookResponseModel>>() {}.type

        val user: UserModel? = getLoggedIn()
        val booksObj: ArrayList<BookResponseModel> = Gson().fromJson(jsonString, booksListType)
        Log.e(TAG, "bookObj: $booksObj")

        val cartBookList = user?.cartBookList
        val cartBookIds = cartBookList?.map {
            it.bookId
        } ?: emptyList()
            Log.e(TAG, "print: $user")

        return booksObj.map {
            BookModel(it).apply {
                isCarted = cartBookIds.contains(it.bookId)
            }
        }
    }

    fun getCartItemBooks(): ArrayList<BookModel>? {
        val cartItemBooks: ArrayList<BookModel> = ArrayList()
        for (book in getCartBookList()) {
            if (book.isCarted) {
                cartItemBooks.add(book)
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
}

