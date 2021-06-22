package com.bridgelabz.UI.bookList.adapter

import android.util.Log
import android.view.View
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bridgelabz.UI.model.ReviewModel
import com.bridgelabz.bookstore.R

class ReviewViewHolder(view: View) :
    RecyclerView.ViewHolder(view) {

    private var TAG = "BookViewHolder"
    private val reviewAuthorNameTextView = view.findViewById<TextView>(R.id.reviewAuthorTextView)
    private val reviewRatingBar = view.findViewById<RatingBar>(R.id.reviewRatingBar)
    private val reviewDescriptionTextView =
        view.findViewById<TextView>(R.id.reviewDescriptionTextView)

    fun bind(bookReviewsList: ReviewModel) {
        Log.e(TAG, "bind: $bookReviewsList")
        reviewAuthorNameTextView.text = bookReviewsList.author
        reviewRatingBar.rating  = bookReviewsList.rating
        reviewDescriptionTextView.text = bookReviewsList.review

    }
}
