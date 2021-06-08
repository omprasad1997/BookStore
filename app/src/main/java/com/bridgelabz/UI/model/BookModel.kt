package com.bridgelabz.UI.model

data class BookModel(
    var bookId: Int,
    var bookName: String,
    var bookAuthor: String,
    var bookImage: String,
    var rating: String,
    var review: String,
    var discountedPrice: String,
    var originalPrice: String,
    var discountInPercentage: String,
    var isFavourite: Boolean = false,
    var isCarted: Boolean = false
) {
    constructor(bookResponseModel: BookResponseModel) : this(
        bookResponseModel.bookId,
        bookResponseModel.bookName,
        bookResponseModel.bookAuthor,
        bookResponseModel.bookImage,
        bookResponseModel.rating,
        bookResponseModel.review,
        bookResponseModel.discountedPrice,
        bookResponseModel.originalPrice,
        bookResponseModel.discountInPercentage
    )
}
