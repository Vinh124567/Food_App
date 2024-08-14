package com.example.myapplication.View.Fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.R
import com.example.myapplication.adapter.MyViewPagerAdapter
import com.example.myapplication.databinding.FragmentYourOrdersBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class YourOrdersFragment : Fragment(R.layout.fragment_your_orders) {
    private lateinit var binding: FragmentYourOrdersBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding=FragmentYourOrdersBinding.bind(view)

        setupTabLayoutAndViewPager()
    }

    private fun setupTabLayoutAndViewPager() {
        val tabLayout: TabLayout = binding.tabLayout
        val viewPager2: ViewPager2 = binding.viewPager

        val fragments = listOf(
            InProgressFragment(),
            PastOrdersFragment(),
        )

        val fragmentTitles = listOf(
            "In Progerss",
            "Past Order",
        )

        val myViewPagerAdapter = MyViewPagerAdapter(requireActivity(), fragments,fragmentTitles)
        viewPager2.adapter = myViewPagerAdapter

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = myViewPagerAdapter.getPageTitle(position)
        }.attach()
    }


}