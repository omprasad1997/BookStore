package com.bridgelabz.UI.model

data class ReviewModel(
    val author: String,
    val authorId: Long = System.currentTimeMillis(),
    val reviewId: Long = System.currentTimeMillis(),
    val rating: Float,
    val review: String
)