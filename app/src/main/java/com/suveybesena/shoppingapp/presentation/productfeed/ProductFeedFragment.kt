package com.suveybesena.shoppingapp.presentation.productfeed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.suveybesena.shoppingapp.databinding.FragmentProductFeedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductFeedFragment : Fragment() {

    private val viewModel: ProductFeedViewModel by viewModels()
    private lateinit var binding: FragmentProductFeedBinding
    lateinit var productAdapter: ProductFeedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductFeedBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeData()

    }

    private fun observeData() {

        viewModel._products.observe(viewLifecycleOwner) { resource ->
                productAdapter.differ.submitList(resource)
            }
    }

    private fun setupRecyclerView() {
        productAdapter = ProductFeedAdapter()
        binding.rvShopping.adapter = productAdapter
        binding.rvShopping.layoutManager = GridLayoutManager(requireContext(), 2)
    }
}