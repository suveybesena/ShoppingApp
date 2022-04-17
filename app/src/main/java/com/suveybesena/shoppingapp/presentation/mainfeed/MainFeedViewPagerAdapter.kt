package com.suveybesena.shoppingapp.presentation.mainfeed

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.suveybesena.shoppingapp.presentation.mainfeed.eyeliner.EyelinerFragment
import com.suveybesena.shoppingapp.presentation.mainfeed.eyeshadow.EyeshadowFragment
import com.suveybesena.shoppingapp.presentation.mainfeed.lipstick.LipstickFragment

class MainFeedViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> LipstickFragment()
            1 -> EyeshadowFragment()
            2 -> EyelinerFragment()
            else -> {
                LipstickFragment()
            }
        }
    }
}