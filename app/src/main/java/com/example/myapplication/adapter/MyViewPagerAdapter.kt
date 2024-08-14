package com.example.myapplication.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


//class MyViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
//
//    override fun createFragment(position: Int): Fragment {
//        return when (position) {
//            0 -> NewTasteFragment()
//            1 -> PopularFragment()
//            2 -> RecommendedFragment()
//            else -> NewTasteFragment()
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return 3
//    }
//}

class MyViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val fragments: List<Fragment>,
    private val fragmentTitles: List<String>
) : FragmentStateAdapter(fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    override fun getItemCount(): Int {
        return fragments.size
    }

    fun getPageTitle(position: Int): CharSequence? {
        return fragmentTitles[position]
    }


}

