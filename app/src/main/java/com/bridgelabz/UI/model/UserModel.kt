package com.bridgelabz.UI.model

import com.bridgelabz.UI.model.responsemodel.AddressResponseModel
import com.bridgelabz.UI.model.responsemodel.CartResponseModel
import com.bridgelabz.UI.model.responsemodel.OrderResponseModel
import com.google.gson.annotations.SerializedName

data class UserModel(
    var id: Long = 0,
    val userName: String,
    val email: String,
    val password: String,
    val confirmPassword: String = " ",
    @SerializedName("FavouriteBooksList") val favouriteBookList: ArrayList<Int>,
    @SerializedName("CartBooksList") var cartBookList: ArrayList<CartResponseModel>,
    @SerializedName("usersAddressArray") val userAddress: ArrayList<AddressResponseModel>,
    val orderList: ArrayList<OrderResponseModel>,
    var userProfileImage: String
)
