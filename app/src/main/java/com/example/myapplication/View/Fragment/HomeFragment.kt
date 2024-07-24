package com.example.myapplication.View.Fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.R
import com.example.myapplication.View.MainActivity
import com.example.myapplication.View.viewmodel.FoodViewModel
import com.example.myapplication.adapter.FoodAdapter
import com.example.myapplication.adapter.MyViewPagerAdapter
import com.example.myapplication.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayout

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var foodViewModel: FoodViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var foodAdapter: FoodAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        foodViewModel = (activity as MainActivity).foodViewModel

        setupHomeRecycler()

        foodAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("food", it)
            }
            findNavController().navigate(R.id.action_homeFragment_to_foodDetailFragment2,bundle)
        }

        foodViewModel.foodLiveData.observe(viewLifecycleOwner, Observer { foodList ->
            foodAdapter.differ.submitList(foodList)
        })

        foodViewModel.fetchAllFood()

        setupTabLayoutAndViewPager()
    }

    private fun setupTabLayoutAndViewPager() {
        val tabLayout: TabLayout = binding.tabLayout
        val viewPager2: ViewPager2 = binding.viewPager

        val myViewPagerAdapter = MyViewPagerAdapter(requireActivity())
        viewPager2.adapter = myViewPagerAdapter

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager2.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.getTabAt(position)?.select()
            }
        })
    }

    private fun setupHomeRecycler() {
        foodAdapter = FoodAdapter()
        binding.recyclerHome.apply {
            adapter = foodAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
