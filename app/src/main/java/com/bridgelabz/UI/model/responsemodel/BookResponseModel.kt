package com.bridgelabz.UI.model.responsemodel

import com.google.gson.annotations.SerializedName

data  class BookResponseModel (@SerializedName("Book Id") var bookId : Int,
                               @SerializedName("Book Name") var bookName : String,
                               @SerializedName("Book Author") var bookAuthor : String,
                               @SerializedName("Book Image") var bookImage : String,
                               @SerializedName("Rating") var rating : String,
                               @SerializedName("Review") var review : String,
                               @SerializedName("Discounted Price") var discountedPrice : String,
                               @SerializedName("Original Price") var originalPrice : String,
                               @SerializedName("Discount in Percentage") var discountInPercentage : String)
