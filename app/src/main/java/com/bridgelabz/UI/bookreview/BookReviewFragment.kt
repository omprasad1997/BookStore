package com.bridgelabz.UI.bookreview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bridgelabz.bookstore.R


class BookReviewFragment : Fragment() {
    private lateinit var reviewToolbar: Toolbar

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