package com.bridgelabz.UI.bookList


import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bridgelabz.Constants.Constants
import com.bridgelabz.UI.bookList.adapter.BookAdapter
import com.bridgelabz.UI.datamanager.BookDataManager
import com.bridgelabz.UI.login.LoginActivity
import com.bridgelabz.bookstore.R


class BookFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    var updatingCartItemCount:(()->Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_book, container, false)
        initViews(view)

        return view
    }

    private fun initViews(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        setAdapter()
        updatingCartItemCount?.invoke()
    }

    private fun setAdapter() {

        val bookDataManager =
            BookDataManager(context)
        val bookList = bookDataManager.getBookList()

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val bookAdapter = BookAdapter(bookList) {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.fragment_container, BookDescriptionFragment(it))
                    .addToBackStack(null).commit()
            }
        }
        recyclerView.adapter = bookAdapter

    }
}