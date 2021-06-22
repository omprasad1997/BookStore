package com.bridgelabz.UI.bookList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bridgelabz.UI.model.ReviewModel
import com.bridgelabz.bookstore.R

class ReviewAdapter(private val bookReviewsList: ArrayList<ReviewModel>
) :
    RecyclerView.Adapter<ReviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        return ReviewViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.review_item_layout, parent, false))
    }

    override fun onBindViewHolder(holderBook: ReviewViewHolder, position: Int) {
        holderBook.bind(bookReviewsList[position])
    }

    override fun getItemCount(): Int {
        return bookReviewsList.size
    }
}
