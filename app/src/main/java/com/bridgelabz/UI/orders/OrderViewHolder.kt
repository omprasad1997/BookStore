package com.bridgelabz.UI.orders

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bridgelabz.UI.model.BookModel
import com.bridgelabz.UI.model.responsemodel.OrderResponseModel
import com.bridgelabz.bookstore.R
import com.bumptech.glide.Glide

class OrderViewHolder(
    view: View,
    private val bookList: List<BookModel>,
    private val bookReviewHandler: (position: Int) -> Unit
) : RecyclerView.ViewHolder(view) {

    private var TAG = "OrderViewHolder"
    private val orderIdTextView = view.findViewById<TextView>(R.id.order_Id)
    private val orderDateAndTimeTextView = view.findViewById<TextView>(R.id.order_date_Time)
    private val orderBookImageView = view.findViewById<ImageView>(R.id.orderBookImageView)
    private val bookReviewButton = view.findViewById<Button>(R.id.bookReviewButton)

    init {
        bookReviewButton.setOnClickListener {
            bookReviewHandler(adapterPosition)
        }

    }


    fun bind(orderResponseModel: OrderResponseModel) {
        orderIdTextView.text = orderResponseModel.orderId.toString()
        orderDateAndTimeTextView.text = orderResponseModel.orderDate

        lateinit var storeImageString: String
        val bookId = orderResponseModel.cartItems[0].bookId
        for (i in bookList.indices) {
            if (bookList[i].bookId == bookId) {
                storeImageString = bookList[i].bookImage
                break
            }
        }

        Glide.with(itemView.context)
            .load(
                itemView.context.resources.getIdentifier(
                    storeImageString,
                    "drawable",
                    itemView.context.packageName
                )
            )
            .into(orderBookImageView)
    }
}
