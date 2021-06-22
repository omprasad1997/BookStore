package com.bridgelabz.UI.orders.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bridgelabz.UI.model.BookModel
import com.bridgelabz.UI.model.responsemodel.OrderResponseModel
import com.bridgelabz.UI.orders.OrderViewHolder
import com.bridgelabz.bookstore.R

class OrderAdapter(
    private val orderList: ArrayList<OrderResponseModel>?,
    private val bookList: List<BookModel>,
    private val bookReviewHandler: (orderResponseModel: OrderResponseModel) -> Unit
) :
    RecyclerView.Adapter<OrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder{
        return OrderViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.orders_item_layout, parent, false), bookList, bookReviewHandler)
    }

    override fun onBindViewHolder(holderBook: OrderViewHolder, position: Int) {
        orderList?.get(position)?.let { holderBook.bind(it) }
    }

    override fun getItemCount(): Int {
        return orderList!!.size
    }

}