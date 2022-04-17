package com.suveybesena.shoppingapp.presentation.mainfeed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.suveybesena.shoppingapp.databinding.FragmentMainFeedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFeedFragment : Fragment() {

    private lateinit var binding: FragmentMainFeedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainFeedBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = MainFeedViewPagerAdapter(this)
        binding.vpProductFeed.adapter = adapter
        TabLayoutMediator(
            binding.tabs, binding.vpProductFeed
        ) { tab, position ->
            if (position == 0) {
                tab.text = "Lıpstıck"
            } else if (position == 1) {
                tab.text = "Eyeshadow"
            } else {
                tab.text = "Eyelıner"
            }
        }.attach()
    }
}