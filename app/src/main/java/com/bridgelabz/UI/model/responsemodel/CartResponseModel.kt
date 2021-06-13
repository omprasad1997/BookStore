package com.bridgelabz.UI.model.responsemodel

import com.google.gson.annotations.SerializedName

data class CartResponseModel(
    @SerializedName("bookQuantity")
    val quantityOfBook: Int,
    val bookId: Int
)