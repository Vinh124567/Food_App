package com.example.myapplication.View.Fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.View.MainActivity
import com.example.myapplication.adapter.FoodAdapter
import com.example.myapplication.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var foodViewModel: FoodViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var foodAdapter: FoodAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)


        foodViewModel = (activity as MainActivity).foodViewModel


        setupHomeRecycler()

        foodViewModel.foodLiveData.observe(viewLifecycleOwner, Observer { foodList ->
            foodAdapter.differ.submitList(foodList)
        })

        foodViewModel.fetchAllFood()
    }

    private fun setupHomeRecycler() {
        foodAdapter = FoodAdapter()
        binding.recyclerHome.apply {
            adapter = foodAdapter
            // Sử dụng LinearLayoutManager với orientation ngang
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

}
