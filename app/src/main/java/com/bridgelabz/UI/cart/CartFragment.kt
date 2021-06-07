package com.bridgelabz.UI.cart

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bridgelabz.bookstore.R

class CartFragment : Fragment() {
    private lateinit var cartRecyclerView: RecyclerView
    private lateinit var cartToolbar: Toolbar

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_cart, container, false)
        initViews(view)
        onBackPressed(view)
        return view
    }

    private fun initViews(view: View) {
        cartToolbar = view.findViewById(R.id.cart_toolbar)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun onBackPressed(view: View) {

        cartToolbar.title = "Cart"
        cartToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        cartToolbar.setNavigationOnClickListener { //handle any click event
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