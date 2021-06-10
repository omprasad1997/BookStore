package com.bridgelabz.UI.bookList


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bridgelabz.UI.bookList.adapter.BookAdapter
import com.bridgelabz.bookstore.R


class BookFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView

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
    }

    private fun setAdapter() {

        val bookDataManager = BookDataManager(context)
        val bookList = bookDataManager.getBookList()

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val bookAdapter = BookAdapter(bookList) {
            Toast.makeText(context, "book clicked ${it.bookName}", Toast.LENGTH_SHORT).show()
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.fragment_container, BookDescriptionFragment(it))
                    .addToBackStack(null).commit()
            }
        }
        recyclerView.adapter = bookAdapter

    }
}