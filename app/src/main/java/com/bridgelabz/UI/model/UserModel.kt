package com.bridgelabz.UI.model

import com.google.gson.annotations.SerializedName

data class UserRegistrationModel(
    var id:Long = 0,
    val userName: String,
    val email: String,
    val password: String,
    val confirmPassword: String = " ",
    @SerializedName("FavouriteBooksList")val favouriteBookList: ArrayList<Int>,
    @SerializedName("CartBooksList")val cartBookList: ArrayList<CartResponseModel>
)
