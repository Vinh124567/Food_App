package com.example.myapplication.adapter


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapplication.View.Fragment.NewTasteFragment
import com.example.myapplication.View.Fragment.PopularFragment
import com.example.myapplication.View.Fragment.RecommendedFragment


class MyViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> NewTasteFragment()
            1 -> PopularFragment()
            2 -> RecommendedFragment()
            else -> NewTasteFragment()
        }
    }

    override fun getItemCount(): Int {
        return 3
    }
}
