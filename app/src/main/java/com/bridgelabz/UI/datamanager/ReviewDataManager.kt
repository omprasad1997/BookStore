package com.bridgelabz.UI.datamanager

import android.content.Context
import android.util.Log
import com.bridgelabz.UI.model.ReviewResponseModel
import com.bridgelabz.UI.model.ReviewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class ReviewDataManager(val context: Context) {

    private val TAG = "ReviewDataManager"

    fun getListOfBookReviews(): ArrayList<ReviewResponseModel>? {

        val path = context.filesDir?.absolutePath
        val file = File(path, "reviews.json")

        val bookReviewListType = object : TypeToken<ArrayList<ReviewResponseModel>>() {}.type
        val listOfReviewResponses: ArrayList<ReviewResponseModel> =
            if (file.exists()) Gson().fromJson(file.readText(), bookReviewListType)
            else ArrayList()

        return listOfReviewResponses

    }

    fun getListOfRandomReview(): java.util.ArrayList<ReviewModel> {
        val reviewList = ArrayList<ReviewModel>()
        val ratingList = listOf(2.5f, 3.5f, 4.5f, 5.0f)
        var randomUserName: String?
        var pickRatingOfRandomUser: Float?
        var review: String?
        for (i in 1..10) {
            randomUserName = "Random User $i"
            pickRatingOfRandomUser = ratingList.random()
            review = when (pickRatingOfRandomUser) {
                2.5f -> "Not good book"
                3.5f -> "Fine book"
                4.5f -> "Excellent book"
                else -> "Beautiful book"
            }

            val userReview = ReviewModel(
                randomUserName,
                System.currentTimeMillis(),
                System.currentTimeMillis(),
                pickRatingOfRandomUser,
                review
            )
            reviewList.add(userReview)
        }

        Log.e(TAG, "getListOfRandomReview: $reviewList")
        return reviewList
    }
}