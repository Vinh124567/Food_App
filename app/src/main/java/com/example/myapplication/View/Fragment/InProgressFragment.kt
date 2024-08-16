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
import com.example.myapplication.databinding.FragmentInProgressBinding


class InProgressFragment : Fragment(R.layout.fragment_in_progress) {
    private lateinit var foodOrderAdapter: FoodOrderAdapter
    private lateinit var authViewModel: AuthViewModel
    private lateinit var binding: FragmentInProgressBinding
    private lateinit var orderViewModel: OrderViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInProgressBinding.bind(view)
        orderViewModel= (activity as MainActivity).orderViewModel
        authViewModel= (activity as MainActivity).authViewModel
        setupHomeRecycler()

        orderViewModel.listOrderResult.observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                val filteredList = it.filter { order ->
                    order.state =="Processing"
                }
                foodOrderAdapter.differ.submitList(filteredList)
            }
        })


        authViewModel.authResult.observe(viewLifecycleOwner, Observer { signInResult ->
            if (signInResult != null) {
                val user = signInResult.first
                user?.uid?.let { uid ->
                    orderViewModel.getOrder(uid)
                }
            } else {
            }
        })

    }

    private fun setupHomeRecycler() {
        foodOrderAdapter = FoodOrderAdapter()
        binding.recyclerViewInProgress.apply {
            adapter = foodOrderAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        }
    }
}