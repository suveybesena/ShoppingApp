package com.suveybesena.shoppingapp.presentation.productfeed.mainfeed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.suveybesena.shoppingapp.R
import com.suveybesena.shoppingapp.databinding.FragmentProductFeedBinding
import com.suveybesena.shoppingapp.adapter.ProductsViewPagerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductFeedFragment : Fragment() {





    private lateinit var binding: FragmentProductFeedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductFeedBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ProductsViewPagerAdapter(this)
        binding.vpProductFeed.adapter = adapter
        TabLayoutMediator(binding.tabs,binding.vpProductFeed
        ) { tab, position -> if (position==0){tab.text="Lipstick"}
        else if(position ==1) {tab.text ="Eyeshadow"}
        else {tab.text ="Eyeliner"}
        }.attach()


    }



}