package com.example.todonotesapp.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentAdapter(fm : FragmentActivity) : FragmentStateAdapter(fm){

    override fun getItemCount(): Int {

        return 2
    }

    override fun createFragment(position: Int): Fragment {

        when (position) {
            0 -> return OnBoardingOneFragment()
            1 -> return OnBoardingTwoFragment()
            else -> return Fragment()
        }
    }


}