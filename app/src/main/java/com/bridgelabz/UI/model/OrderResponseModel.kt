package com.bridgelabz.UI.model

data class OrderResponseModel(
    val orderId: String,
    val orderTotal: String,
    val cartItems: List<CartResponseModel>,
    val orderDate: String
)