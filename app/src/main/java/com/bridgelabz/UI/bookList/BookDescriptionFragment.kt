package com.bridgelabz.UI.bookList

import android.content.Context
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.bridgelabz.HelperClass.SharedPreferenceHelper
import com.bridgelabz.UI.bookList.adapter.ReviewAdapter
import com.bridgelabz.UI.datamanager.ReviewDataManager
import com.bridgelabz.UI.model.BookModel
import com.bridgelabz.UI.datamanager.UserDataManager
import com.bridgelabz.UI.model.ReviewModel
import com.bridgelabz.bookstore.R
import com.bridgelabz.workmanager.CartWorker
import com.bumptech.glide.Glide
import org.json.simple.JSONArray
import org.json.simple.JSONObject

class BookDescriptionFragment(private val book: BookModel) : Fragment() {
    private lateinit var bookImageImageView: ImageView
    private lateinit var bookTitleTextView: TextView
    private lateinit var bookAuthorNameTextView: TextView
    private lateinit var priceOfBookTextView: TextView
    private lateinit var discountedPriceOfBookTextView: TextView
    private lateinit var descriptionOfBookTextView: TextView
    private lateinit var addToCartButton: Button
    private lateinit var bookDescriptionToolbar: Toolbar
    private lateinit var reviewRecyclerView: RecyclerView
    private lateinit var ratingOfBookTextView: TextView
    private lateinit var reviewNumberTextView: TextView
    private val TAG = "BookDescriptionFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_book_discription, container, false)

        initViews(view)
        onBackPressed()
        return view
    }


    private fun initViews(view: View?) {

        if (view != null) {
            bookTitleTextView = view.findViewById(R.id.bookTitle)
            bookImageImageView = view.findViewById(R.id.imageOfBook)
            bookAuthorNameTextView = view.findViewById(R.id.bookAuthorName)
            priceOfBookTextView = view.findViewById(R.id.priceOfBook)
            discountedPriceOfBookTextView =
                view.findViewById(R.id.discounted_price_of_book_text_view)
            descriptionOfBookTextView = view.findViewById(R.id.description_of_book_text_view)
            addToCartButton = view.findViewById(R.id.addToCartButton)
            bookDescriptionToolbar = view.findViewById(R.id.book_description_toolbar)
            reviewRecyclerView = view.findViewById(R.id.reviewRecyclerView)
            ratingOfBookTextView = view.findViewById(R.id.ratingOfBookTextView)
            reviewNumberTextView = view.findViewById(R.id.reviewNumberTextView)
        }
        setContentBook()
        loadReviewsOfBook(view!!.context)

    }

    private fun loadReviewsOfBook(context: Context): ArrayList<ArrayList<ReviewModel>> {
        val reviewDataManager = ReviewDataManager(context)

        val listOfBookReviews = reviewDataManager.getListOfBookReviews()
        val randomUserReviewList: ArrayList<ReviewModel> = reviewDataManager.getListOfRandomReview()


        Log.e(TAG, "loadReviewsOfBook:listOfBookReviews $listOfBookReviews")
        val reviewList = ArrayList<ArrayList<ReviewModel>>()
        var storeBookId: Int = 0
        var listOfReview = ArrayList<ReviewModel>()

        if (listOfBookReviews!!.isEmpty()) {
            listOfReview = randomUserReviewList

        } else {
            for (i in listOfBookReviews.indices) {
                if (listOfBookReviews[i].bookId == book.bookId) {
                    storeBookId = listOfBookReviews[i].bookId
                    reviewList.add(listOfBookReviews[i].reviews)
                }
            }

            for (i in reviewList.indices) {
                listOfReview.add(reviewList[i][0])
            }

            listOfReview.let { list1 ->
                randomUserReviewList.let(list1::addAll)
            }
        }

        Log.e(TAG, "loadReviewsOfBook: store $listOfReview")

        if (storeBookId == book.bookId) {
            val reviewAdapter = ReviewAdapter(listOfReview)
            reviewRecyclerView.adapter = reviewAdapter
            reviewAdapter.notifyDataSetChanged()

            calculateAverageRatingOfBook(listOfReview)
        } else {
            val reviewAdapter = ReviewAdapter(listOfReview)
            reviewRecyclerView.adapter = reviewAdapter
            reviewAdapter.notifyDataSetChanged()

            calculateAverageRatingOfBook(listOfReview)
        }

        return reviewList
    }

    private fun calculateAverageRatingOfBook(bookReviews: ArrayList<ReviewModel>) {
        val totalReview = bookReviews.size
        val listOfRating = bookReviews.map {
            it.rating
        }
        Log.e(TAG, "loadReviewsOfBook: $listOfRating")
        val averageRatingOfBook = String.format("%.1f", listOfRating.sum() / totalReview)
        Log.e(TAG, "loadReviewsOfBook: $averageRatingOfBook")

        ratingOfBookTextView.text = averageRatingOfBook
        reviewNumberTextView.text = totalReview.toString()
    }

    private fun setContentBook() {
        bookTitleTextView.text = book.bookName
        bookAuthorNameTextView.text = book.bookAuthor

        discountedPriceOfBookTextView.apply {
            paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            text = book.discountedPrice
        }

        descriptionOfBookTextView.text = book.description

        priceOfBookTextView.text = book.originalPrice
        addToCartButton.isEnabled = !book.isCarted
        Glide.with(requireContext())
            .load(
                requireContext().resources.getIdentifier(
                    book.bookImage,
                    "drawable",
                    requireContext().packageName
                )
            )
            .into(bookImageImageView)

        reviewRecyclerView.layoutManager = LinearLayoutManager(view?.context)
    }

    private fun onBackPressed() {
        bookDescriptionToolbar.title = "Book Store"
        bookDescriptionToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        bookDescriptionToolbar.setNavigationOnClickListener {
            Log.e(TAG, "onBackPressed: called")
            activity?.supportFragmentManager?.popBackStack()
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as? AppCompatActivity)?.supportActionBar?.hide()

        addToCartButton.setOnClickListener {
            Toast.makeText(it.context, "${book.bookName} is added to cart", Toast.LENGTH_SHORT)
                .show()
            setCartItem(book.bookId, it.context)
            addToCartButton.isEnabled = false
            setOneTimeWorkRequest(it.context)
        }
    }

    private fun setOneTimeWorkRequest(context: Context) {
        val oneTimeWorkRequest = OneTimeWorkRequest.Builder(CartWorker::class.java).build()
        WorkManager.getInstance(context).enqueue(oneTimeWorkRequest)
    }


    private fun setCartItem(bookId: Int, context: Context) {
        val userDataManager =
            UserDataManager(context)
        val sharedPreferenceHelper = SharedPreferenceHelper(context)

        val jsonArray = userDataManager.readDataFromJSONFile()

        for (i in 0 until jsonArray.size) {
            val usersJSONObj = jsonArray[i] as JSONObject
            Log.e(
                TAG,
                "favouriteChecked: ${sharedPreferenceHelper.getLoggedInUserId()} : $usersJSONObj"
            )
            if (sharedPreferenceHelper.getLoggedInUserId() == usersJSONObj["id"]) {
                val cartBookList = usersJSONObj["CartBooksList"] as JSONArray
                val cartJsonObj = JSONObject()
                cartJsonObj["bookQuantity"] = 1
                cartBookList.add(cartJsonObj)
                cartJsonObj["bookId"] = bookId
                usersJSONObj["CartBooksList"] = cartBookList

                val fos = context.openFileOutput("use_credential.json", Context.MODE_PRIVATE)
                fos.write(jsonArray.toString().toByteArray())
                fos.close()
                break
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as? AppCompatActivity)?.supportActionBar?.show()
    }
}
