package com.suveybesena.shoppingapp.presentation.productfeed.eyeliner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.suveybesena.shoppingapp.R
import com.suveybesena.shoppingapp.databinding.FragmentEyelinerBinding
import com.suveybesena.shoppingapp.presentation.productfeed.ProductFeedAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EyelinerFragment : Fragment() {

    private val viewModel : EyelinerViewModel by viewModels()
    private lateinit var eyelinerAdapter : ProductFeedAdapter
    private lateinit var binding: FragmentEyelinerBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentEyelinerBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeData()

    }

    private fun observeData() {
        viewModel._uiState.observe(viewLifecycleOwner, Observer { state->
            if (state.productItems != null){
                eyelinerAdapter.differ.submitList(state.productItems)
            }

        })


    }

    private fun setupRecyclerView() {
       eyelinerAdapter = ProductFeedAdapter()
        binding.apply {
            rvEyeliner.adapter = eyelinerAdapter
            rvEyeliner.layoutManager = GridLayoutManager( requireContext(), 2)
        }
    }

}