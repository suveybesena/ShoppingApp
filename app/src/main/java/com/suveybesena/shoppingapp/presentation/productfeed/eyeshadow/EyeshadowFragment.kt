package com.suveybesena.shoppingapp.presentation.productfeed.eyeshadow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.suveybesena.shoppingapp.R
import com.suveybesena.shoppingapp.databinding.FragmentEyeshadowBinding
import com.suveybesena.shoppingapp.presentation.productfeed.ProductFeedAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EyeshadowFragment : Fragment() {
    private val viewModel : EyeShadowViewModel by viewModels()
    lateinit var eyeshadowAdapter: ProductFeedAdapter
    private lateinit var  binding : FragmentEyeshadowBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEyeshadowBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeData()

    }

    private fun observeData() {
      viewModel._uiState.observe(viewLifecycleOwner, Observer {  state->
          if (state.productItems != null){
              eyeshadowAdapter.differ.submitList(state.productItems)
          }

      })
    }

    private fun setupRecyclerView() {
        eyeshadowAdapter = ProductFeedAdapter()
        binding.apply {
            rvEyeshadow.adapter = eyeshadowAdapter
            rvEyeshadow.layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

}