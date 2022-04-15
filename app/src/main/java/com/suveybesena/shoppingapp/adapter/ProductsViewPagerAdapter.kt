package com.suveybesena.shoppingapp.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.suveybesena.shoppingapp.presentation.productfeed.eyeliner.EyelinerFragment
import com.suveybesena.shoppingapp.presentation.productfeed.eyeshadow.EyeshadowFragment
import com.suveybesena.shoppingapp.presentation.productfeed.lipstick.LipstickFragment

class ProductsViewPagerAdapter (fragment: Fragment) :FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
         return when(position){
            0 -> LipstickFragment()
            1 -> EyeshadowFragment()
            2-> EyelinerFragment()
            else -> {
                LipstickFragment()
            }
        }
    }


}