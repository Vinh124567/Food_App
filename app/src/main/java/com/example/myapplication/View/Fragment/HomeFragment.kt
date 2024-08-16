package com.example.myapplication.View.Fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
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
import com.google.android.material.tabs.TabLayoutMediator

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
        initAdapter()
        observeViewModel()
        setupTabLayoutAndViewPager()
    }


    private fun initAdapter() {
        foodAdapter.setOnItemClickListener {
            foodViewModel.setCount(1)
            val bundle = Bundle().apply {
                putSerializable("food", it)
            }
            findNavController().navigate(R.id.action_homeFragment_to_foodDetailFragment2,bundle)
        }

    }

    private fun observeViewModel() {
        foodViewModel.listFoodLiveData.observe(viewLifecycleOwner, Observer { foodList ->
            foodAdapter.differ.submitList(foodList)
        })

        foodViewModel.errorLiveData.observe(viewLifecycleOwner, Observer { errorMessage ->
            errorMessage?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupTabLayoutAndViewPager() {
    val tabLayout: TabLayout = binding.tabLayout
    val viewPager2: ViewPager2 = binding.viewPager

    val fragments = listOf(
        NewTasteFragment(),
        PopularFragment(),
        RecommendedFragment()
    )

    val fragmentTitles = listOf(
        "New Taste",
        "Popular",
        "Recommended"
    )
    val myViewPagerAdapter = MyViewPagerAdapter(requireActivity(), fragments,fragmentTitles)
    viewPager2.adapter = myViewPagerAdapter

    TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
        tab.text = myViewPagerAdapter.getPageTitle(position)
    }.attach()
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
