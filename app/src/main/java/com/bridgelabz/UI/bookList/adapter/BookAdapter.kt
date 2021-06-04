package com.bridgelabz.UI.bookList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bridgelabz.UI.model.BookModel
import com.bridgelabz.bookstore.R

class BookAdapter(
    private val bookItems: ArrayList<BookModel>,
    private val handler: (book: BookModel) -> Unit
) :
    RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.book_item_layout, parent, false), handler
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(bookItems[position])
    }

    override fun getItemCount(): Int {
        return bookItems.size
    }
}
