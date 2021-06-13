package com.bridgelabz.UI.model.responsemodel

import com.bridgelabz.UI.model.responsemodel.CartResponseModel

data class OrderResponseModel(
    val orderId: Long,
    val orderTotal: Double,
    val cartItems: List<CartResponseModel>,
    val orderDate: String
)