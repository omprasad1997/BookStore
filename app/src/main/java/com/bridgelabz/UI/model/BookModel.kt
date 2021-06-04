package com.bridgelabz.UI.model

import com.google.gson.annotations.SerializedName

//data class BookModel(val bookName: String = "", val bookAuthor: String= "", val bookImage:String= "",
//                     val rating:String= "", val review:String= "", val discountedPrice:String= "",
//                     val originalPrice: String= "", val discountInPercentage:String= "")

data  class BookModel (@SerializedName("Book Name") var bookName : String,
                   @SerializedName("Book Author") var bookAuthor : String,
                   @SerializedName("Book Image") var bookImage : String,
                   @SerializedName("Rating") var rating : String,
                   @SerializedName("Review") var review : String,
                   @SerializedName("Discounted Price") var discountedPrice : String,
                   @SerializedName("Original Price") var originalPrice : String,
                   @SerializedName("Discount in Percentage") var discountInPercentage : String)