package com.example.myapplication.View.Fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.View.MainActivity
import com.example.myapplication.View.viewmodel.FoodViewModel
import com.example.myapplication.adapter.FoodOrderAdapter
import com.example.myapplication.databinding.FragmentPastOrdersBinding


class PastOrdersFragment : Fragment(R.layout.fragment_past_orders) {
    private lateinit var foodOrderAdapter: FoodOrderAdapter
    private lateinit var foodViewModel: FoodViewModel
    private lateinit var binding: FragmentPastOrdersBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPastOrdersBinding.bind(view)
        foodViewModel = (activity as MainActivity).foodViewModel

        setupHomeRecycler()

        foodViewModel.listOrderResult.observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                val filteredList = it.filter { order ->
                    order.state =="Delivered"
                }
                foodOrderAdapter.differ.submitList(filteredList)
            }
        })


        foodViewModel.emailResult.observe(viewLifecycleOwner, Observer { user ->
            if (user != null) {
                foodViewModel.getOrder(user.uid)
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