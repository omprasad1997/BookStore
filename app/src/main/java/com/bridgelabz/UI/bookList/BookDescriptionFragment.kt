package com.bridgelabz.UI.bookList

import android.content.Context
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
import com.bridgelabz.HelperClass.SharedPreferenceHelper
import com.bridgelabz.UI.model.BookModel
import com.bridgelabz.UI.register.UserDataManager
import com.bridgelabz.bookstore.R
import com.bumptech.glide.Glide
import org.json.simple.JSONObject

class BookDescriptionFragment(private val book: BookModel) : Fragment() {
    private lateinit var bookImage: ImageView
    private lateinit var bookTitle: TextView
    private lateinit var bookAuthorName: TextView
    private lateinit var priceOfBook: TextView
    private lateinit var addToCartButton: Button
    private lateinit var bookDescriptionToolbar: Toolbar
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
            bookTitle = view.findViewById(R.id.bookTitle)
            bookImage = view.findViewById(R.id.imageOfBook)
            bookAuthorName = view.findViewById(R.id.bookAuthorName)
            priceOfBook = view.findViewById(R.id.priceOfBook)
            addToCartButton = view.findViewById(R.id.addToCartButton)
            bookDescriptionToolbar = view.findViewById(R.id.book_description_toolbar)
        }
        setContentBook()
    }

    private fun setContentBook() {
        bookTitle.text = book.bookName
        bookAuthorName.text = book.bookAuthor
        priceOfBook.text = book.originalPrice
        Glide.with(requireContext())
            .load(
                requireContext().resources.getIdentifier(
                    book.bookImage,
                    "drawable",
                    requireContext().packageName
                )
            )
            .into(bookImage)
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
        }
    }

    private fun setCartItem(bookId: Int, context: Context) {
        val userDataManager = UserDataManager(context)
        val sharedPreferenceHelper = SharedPreferenceHelper(context)

            val jsonArray = userDataManager.readDataFromJSONFile()

            for (i in 0 until jsonArray.size) {
                val usersJSONObj = jsonArray[i] as JSONObject
                Log.e(
                    TAG,
                    "favouriteChecked: ${sharedPreferenceHelper.getLoggedInUserId()} : $usersJSONObj"
                )
                if (sharedPreferenceHelper.getLoggedInUserId() == usersJSONObj["id"]) {
                    val cartBookList = usersJSONObj["CartBooksList"] as ArrayList<Int>
                    cartBookList.add(bookId)
                    usersJSONObj["CartBooksList"] = cartBookList
//                    jsonArray.add(usersJSONObj)
//
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
