package com.bridgelabz.UI.bookList

import android.content.Context
import android.util.Log
import com.bridgelabz.UI.model.BookModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.nio.charset.Charset

class BookDataManager(private val context: Context?) {
    val TAG = "BookDataManager"
    
    fun getBookList(): ArrayList<BookModel> {
        val bookList: ArrayList<BookModel> = ArrayList()
        val jsonString = getJSONFromAssets()!!
        val booksListType = object : TypeToken<Array<BookModel>>() {}.type

        val booksObj: Array<BookModel> = Gson().fromJson(jsonString, booksListType)
        Log.e(TAG, "bookObj: $booksObj")

        booksObj.forEach {
            bookList.add(it)
            Log.e(TAG, "Books: $it")
        }
        return bookList
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