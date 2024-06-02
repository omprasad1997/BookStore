package com.bridgelabz.UI.model

import android.os.Parcel
import android.os.Parcelable
import com.bridgelabz.UI.model.responsemodel.BookResponseModel

data class BookModel(
    var bookId: Int,
    var bookName: String?,
    var bookAuthor: String?,
    var bookImage: String?,
    var rating: String?,
    var review: String?,
    var discountedPrice: String?,
    var originalPrice: String?,
    var discountInPercentage: String?,
    var description:String?,
    var isFavourite: Boolean = false,
    var isCarted: Boolean = false
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    constructor(bookResponseModel: BookResponseModel) : this(
        bookResponseModel.bookId,
        bookResponseModel.bookName,
        bookResponseModel.bookAuthor,
        bookResponseModel.bookImage,
        bookResponseModel.rating,
        bookResponseModel.review,
        bookResponseModel.discountedPrice,
        bookResponseModel.originalPrice,
        bookResponseModel.discountInPercentage,
        bookResponseModel.description
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(bookId)
        parcel.writeString(bookName)
        parcel.writeString(bookAuthor)
        parcel.writeString(bookImage)
        parcel.writeString(rating)
        parcel.writeString(review)
        parcel.writeString(discountedPrice)
        parcel.writeString(originalPrice)
        parcel.writeString(discountInPercentage)
        parcel.writeString(description)
        parcel.writeByte(if (isFavourite) 1 else 0)
        parcel.writeByte(if (isCarted) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BookModel> {
        override fun createFromParcel(parcel: Parcel): BookModel {
            return BookModel(parcel)
        }

        override fun newArray(size: Int): Array<BookModel?> {
            return arrayOfNulls(size)
        }
    }
}
