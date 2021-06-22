package com.bridgelabz.UI.bookList.adapter

import android.content.Context
import android.graphics.Paint
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bridgelabz.HelperClass.SharedPreferenceHelper
import com.bridgelabz.UI.model.BookModel
import com.bridgelabz.UI.datamanager.UserDataManager
import com.bridgelabz.bookstore.R
import com.bumptech.glide.Glide
import org.json.simple.JSONObject

class BookViewHolder(view: View, handler: (bookResponse: BookModel) -> Unit) :
    RecyclerView.ViewHolder(view) {

    private lateinit var bookResponseModel: BookModel
    private lateinit var userDataManager: UserDataManager
    private var TAG = "BookViewHolder"
    private lateinit var sharedPreferenceHelper: SharedPreferenceHelper
    private val bookNameTitle = view.findViewById<TextView>(R.id.bookName)
    private val bookAuthor = view.findViewById<TextView>(R.id.bookAuthor)
    private val bookImage = view.findViewById<ImageView>(R.id.bookImage)
    private val ratingOfBook = view.findViewById<TextView>(R.id.ratingOfBook)
    private val reviewCount = view.findViewById<TextView>(R.id.reviewCount)
    private val discountedPriceOfBook = view.findViewById<TextView>(R.id.discountedPriceOfBook)
    private val originalPriceOfBook = view.findViewById<TextView>(R.id.originalPriceOfBook)
    private val discountInPercentage = view.findViewById<TextView>(R.id.discountPriceInPercentage)
    private val addFavouriteButton = view.findViewById<CheckBox>(R.id.addFavouriteButton)

    init {
        itemView.setOnClickListener {
            if (::bookResponseModel.isInitialized) handler(bookResponseModel)
        }
    }

    private fun favouriteChecked(bookResponseModel: BookModel, checked: Boolean) {
        userDataManager =
            UserDataManager(itemView.context)
        sharedPreferenceHelper = SharedPreferenceHelper(itemView.context)

        val bookId = bookResponseModel.bookId
        if (checked) {
            val jsonArray = userDataManager.readDataFromJSONFile()

            for (i in 0 until jsonArray.size) {
                val usersJSONObj = jsonArray[i] as JSONObject
                Log.e(
                    TAG,
                    "favouriteChecked: ${sharedPreferenceHelper.getLoggedInUserId()} : $usersJSONObj"
                )
                if (sharedPreferenceHelper.getLoggedInUserId() == usersJSONObj["id"]) {
                    val favouriteList = usersJSONObj["FavouriteBooksList"] as ArrayList<Int>
                    favouriteList.add(bookId)
                    usersJSONObj["FavouriteBooksList"] = favouriteList
//                    jsonArray.add(usersJSONObj)
//
                    val fos =
                        itemView.context.openFileOutput("use_credential.json", Context.MODE_PRIVATE)
                    fos.write(jsonArray.toString().toByteArray())
                    fos.close()
                    break
                }
            }


        } else {
            val jsonArray = userDataManager.readDataFromJSONFile()
            val favouriteList: ArrayList<Int>

            for (i in 0 until jsonArray.size) {
                val obj = jsonArray[i] as JSONObject
                Log.e(TAG, "favouriteChecked: ${sharedPreferenceHelper.getLoggedInUserId()} : $obj")
                var bookIndex: Int = -1
                if (sharedPreferenceHelper.getLoggedInUserId() == obj["id"]) {
                    favouriteList = obj["FavouriteBooksList"] as ArrayList<Int>
                    favouriteList.forEachIndexed { index, value ->
                        if (value == bookId) bookIndex = index
                    }

                    Log.e(TAG, "favouriteChecked:index $bookIndex ")
                    Log.e(TAG, "favouriteChecked: $favouriteList ")
                    Log.e(TAG, "favouriteChecked: $bookId")
                    if (bookIndex != -1) favouriteList.removeAt(bookIndex)
                    obj["FavouriteBooksList"] = favouriteList
//                    jsonArray.add(obj)

                    val fos =
                        itemView.context.openFileOutput("use_credential.json", Context.MODE_PRIVATE)
                    fos.write(jsonArray.toString().toByteArray())
                    fos.close()

                    break
                }
            }
        }
    }

    fun bind(bookResponseModel: BookModel) {
        this.bookResponseModel = bookResponseModel
        bookNameTitle.text = bookResponseModel.bookName
        bookAuthor.text = bookResponseModel.bookAuthor
        ratingOfBook.text = bookResponseModel.rating
        reviewCount.text = bookResponseModel.review
        discountedPriceOfBook.text = bookResponseModel.discountedPrice

        originalPriceOfBook.apply {
            paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            text = bookResponseModel.originalPrice
        }

        discountInPercentage.text = bookResponseModel.discountInPercentage
        addFavouriteButton.isChecked = bookResponseModel.isFavourite

        addFavouriteButton.setOnCheckedChangeListener { buttonView, isChecked ->
            favouriteChecked(bookResponseModel, isChecked)

            addFavouriteButton.isChecked = isChecked
            Log.e(TAG, "checkbox: $isChecked")
        }

        Glide.with(itemView.context)
            .load(
                itemView.context.resources.getIdentifier(
                    bookResponseModel.bookImage,
                    "drawable",
                    itemView.context.packageName
                )
            )
            .into(bookImage)
    }
}
