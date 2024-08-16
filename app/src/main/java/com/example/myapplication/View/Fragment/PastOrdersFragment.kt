package com.example.myapplication.View.Fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.View.MainActivity
import com.example.myapplication.View.viewmodel.AuthViewModel
import com.example.myapplication.View.viewmodel.OrderViewModel
import com.example.myapplication.adapter.FoodOrderAdapter
import com.example.myapplication.databinding.FragmentPastOrdersBinding


class PastOrdersFragment : Fragment(R.layout.fragment_past_orders) {
    private lateinit var foodOrderAdapter: FoodOrderAdapter
    private lateinit var binding: FragmentPastOrdersBinding
    private lateinit var authViewModel: AuthViewModel
    private lateinit var orderViewModel: OrderViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPastOrdersBinding.bind(view)

        authViewModel = (activity as MainActivity).authViewModel
        orderViewModel= (activity as MainActivity).orderViewModel
        setupHomeRecycler()

        orderViewModel.listOrderResult.observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                val filteredList = it.filter { order ->
                    order.state =="Delivered"
                }
                foodOrderAdapter.differ.submitList(filteredList)
            }
        })


        authViewModel.authResult.observe(viewLifecycleOwner, Observer { user ->
            if (user != null) {
                user.first?.uid?.let { orderViewModel.getOrder(it) }
            } else {
            }
        })
    }

    private fun setupHomeRecycler() {
        foodOrderAdapter = FoodOrderAdapter()
        binding.recyclerViewPastOrder.apply {
            adapter = foodOrderAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        }
    }
}