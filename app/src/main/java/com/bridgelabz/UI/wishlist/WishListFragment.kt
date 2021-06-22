package com.bridgelabz.UI.wishlist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bridgelabz.UI.datamanager.BookDataManager
import com.bridgelabz.UI.bookList.BookDescriptionFragment
import com.bridgelabz.UI.bookList.adapter.BookAdapter
import com.bridgelabz.UI.model.BookModel
import com.bridgelabz.bookstore.R


class WishListFragment : Fragment() {
    private lateinit var wishListRecyclerView: RecyclerView
    private lateinit var bookAdapter: BookAdapter
    private lateinit var bookDataManager: BookDataManager
    private lateinit var wishListToolbar: Toolbar


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_wish_list, container, false)
        initViews(view)
        onBackPressed()
        return view
    }

    private fun initViews(view: View?) {
        if (view != null) {
            wishListRecyclerView = view.findViewById(R.id.wishListRecyclerView)
            wishListToolbar = view.findViewById(R.id.wish_list_toolbar)
            bookDataManager =
                BookDataManager(context)
        }
        setUpAdapter()
    }

    private fun setUpAdapter() {
        val favourites: ArrayList<BookModel> = getFavouriteBooks()
        wishListRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        bookAdapter = BookAdapter(favourites) {
            Toast.makeText(context, "book clicked ${it.bookName}", Toast.LENGTH_SHORT).show()
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.fragment_container, BookDescriptionFragment(it)).addToBackStack(null)
                    .commit()
            }
        }
        wishListRecyclerView.adapter = bookAdapter
        bookAdapter.notifyDataSetChanged()
    }

    private fun getFavouriteBooks(): ArrayList<BookModel> {
        val favouriteBooks = ArrayList<BookModel>()
        val bookDataManager =
            BookDataManager(context)
        for (book in bookDataManager.getBookList()) {
            if (book.isFavourite)
                favouriteBooks.add(book)
        }
        return favouriteBooks
    }

    private fun onBackPressed() {

        wishListToolbar.title = "WishList"
        wishListToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        wishListToolbar.setNavigationOnClickListener {
            Log.e("WishListFragment", "onBackPressed: called")
            activity?.supportFragmentManager?.popBackStack()
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