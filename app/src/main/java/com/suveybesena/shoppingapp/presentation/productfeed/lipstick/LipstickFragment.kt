package com.suveybesena.shoppingapp.presentation.productfeed.lipstick

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.suveybesena.shoppingapp.R
import com.suveybesena.shoppingapp.databinding.FragmentLipstickBinding
import com.suveybesena.shoppingapp.presentation.productfeed.ProductFeedAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LipstickFragment : Fragment() {

    private val viewModel : LipstickViewModel by viewModels()
    private lateinit var binding: FragmentLipstickBinding
    lateinit var productFeedAdapter: ProductFeedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =FragmentLipstickBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeData()

    }

    private fun observeData() {
       viewModel._uiState.observe(viewLifecycleOwner, Observer {  state->
           if (state.productItems!=null){
               productFeedAdapter.differ.submitList(state.productItems)
           }

       })
    }

    private fun setupRecyclerView() {
        productFeedAdapter = ProductFeedAdapter()
        binding.apply {
            rvLipstick.adapter = productFeedAdapter
            rvLipstick.layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }
}