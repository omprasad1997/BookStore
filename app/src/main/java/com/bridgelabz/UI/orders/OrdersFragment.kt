package com.bridgelabz.UI.orders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.bridgelabz.bookstore.R


class OrderFragment : Fragment() {
    private lateinit var orderToolbar: Toolbar
    private lateinit var orderRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_order, container, false)

        initViews(view)
        onBackPressed()
        return view
    }

    private fun initViews(view: View) {
        orderToolbar = view.findViewById(R.id.order_toolbar)
        orderRecyclerView = view.findViewById(R.id.orderRecyclerView)
    }

    private fun onBackPressed() {
        orderToolbar.title = "Orders"
        orderToolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        orderToolbar.setNavigationOnClickListener { //handle any click event
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