package com.bridgelabz.UI.bookreview

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bridgelabz.HelperClass.SharedPreferenceHelper
import com.bridgelabz.UI.model.ReviewResponseModel
import com.bridgelabz.UI.model.ReviewModel
import com.bridgelabz.UI.model.UserModel
import com.bridgelabz.bookstore.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


import java.io.File
import java.io.FileWriter


class BookReviewFragment : Fragment() {
    private lateinit var reviewToolbar: Toolbar
    private lateinit var ratingBar: RatingBar
    private lateinit var writeReviewEditText: EditText
    private lateinit var reviewSubmitButton: Button
    private lateinit var sharedPreferenceHelper: SharedPreferenceHelper
    private val TAG = "BookReviewFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_book_review, container, false)

        initViews(view)
        onBackPressed()
        return view
    }

    private fun initViews(view: View) {
        reviewToolbar = view.findViewById(R.id.review_toolbar)
        ratingBar = view.findViewById(R.id.ratingBar)
        writeReviewEditText = view.findViewById(R.id.writeReviewEditText)
        reviewSubmitButton = view.findViewById(R.id.reviewSubmitButton)
        reviewSubmitButton.setOnClickListener {
            setViews(view)
            Toast.makeText(view.context, "Review saved successfully", Toast.LENGTH_SHORT).show()
            activity?.supportFragmentManager?.popBackStack()
        }

    }

    private fun setViews(view: View) {
        sharedPreferenceHelper = SharedPreferenceHelper(view.context)

        val ratingValue = ratingBar.rating
        val reviews = writeReviewEditText.text.toString()
        val reviewId = System.currentTimeMillis()
        val authorId = sharedPreferenceHelper.getLoggedInUserId()
        val user = getUser(view)

        val args = arguments
        val bookReviewId = args!!.getInt("BookId")
        Log.e(TAG, "bookReviewId(args): $bookReviewId ")
        val reviewModel =
            user?.userName?.let { ReviewModel(it, authorId, reviewId, ratingValue, reviews) }

        val reviewModelList = ArrayList<ReviewModel>()
        reviewModelList.add(reviewModel!!)
        Log.e(TAG, "reviewModelList: $reviewModelList")

        val bookReviewModel = ReviewResponseModel(bookReviewId, reviewModelList)

        writeReviewToFile(view, bookReviewModel)

    }

    private fun writeReviewToFile(
        view: View,
        reviewResponseModel: ReviewResponseModel
    ) {
        val path = view.context?.filesDir?.absolutePath
        val file = File(path, "reviews.json")

        val bookReviewListType = object : TypeToken<ArrayList<ReviewResponseModel>>() {}.type
        val listOfReviewResponses: ArrayList<ReviewResponseModel> =
            if (file.exists()) Gson().fromJson(file.readText(), bookReviewListType)
            else ArrayList()

        listOfReviewResponses.add(reviewResponseModel)

        Log.e(TAG, "listOfBookReviews; $listOfReviewResponses ")
        Log.e(TAG, "writeReviewToFile: $reviewResponseModel")

        val fileWriter = FileWriter(file)
        fileWriter.use {
            Gson().toJson(listOfReviewResponses, it)
        }
    }

    private fun getUser(view: View): UserModel? {
        sharedPreferenceHelper = SharedPreferenceHelper(view.context)
        val path = context?.filesDir?.absolutePath
        val file = File(path, "use_credential.json")
        val userListType = object : TypeToken<ArrayList<UserModel>>() {}.type

        val listOfUsers: ArrayList<UserModel> = Gson().fromJson(file.readText(), userListType)
        return listOfUsers.findLast {
            it.id == sharedPreferenceHelper.getLoggedInUserId()
        }
    }

    private fun onBackPressed() {
        reviewToolbar.title = "Review"
        reviewToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        reviewToolbar.setNavigationOnClickListener { //handle any click event
            parentFragmentManager.popBackStack()
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as? AppCompatActivity)?.supportActionBar?.hide()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        (activity as? AppCompatActivity)?.supportActionBar?.show()
    }

}