package com.example.myapplication.View.Fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentOrderSuccessBinding


class OrderSuccessFragment : Fragment(R.layout.fragment_order_success) {
        private lateinit var binding: FragmentOrderSuccessBinding
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            binding = FragmentOrderSuccessBinding.bind(view)


            binding.btnViewMyOrder.setOnClickListener {
                findNavController().navigate(R.id.action_orderSuccessFragment_to_yourOrdersFragment2)
            }
            binding.btnOrderOtherFood.setOnClickListener {
                findNavController().navigate(R.id.action_orderSuccessFragment_to_homeFragment)
            }
        }


}