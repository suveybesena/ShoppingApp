package com.suveybesena.shoppingapp.presentation.mainfeed.eyeshadow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.suveybesena.shoppingapp.R
import com.suveybesena.shoppingapp.data.model.ProductFeatures
import com.suveybesena.shoppingapp.databinding.FragmentEyeshadowBinding
import com.suveybesena.shoppingapp.presentation.mainfeed.MainFeedAdapter
import com.suveybesena.shoppingapp.presentation.mainfeed.MainFeedEvent
import com.suveybesena.shoppingapp.presentation.mainfeed.ShopOnClickInterface
import com.suveybesena.shoppingapp.presentation.mainfeed.WebViewOnClickInterface
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EyeshadowFragment : Fragment() {

    private val viewModel: EyeShadowViewModel by viewModels()
    lateinit var eyeshadowAdapter: MainFeedAdapter
    private lateinit var binding: FragmentEyeshadowBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEyeshadowBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeData()
    }

    private fun observeData() {
        viewModel.handleEvent(MainFeedEvent.GetMain)
        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                state.eyeshadowItems.let { eyeshadowItemsList ->
                    eyeshadowAdapter.differ.submitList(eyeshadowItemsList)
                }
                state.isLoading.let { boolean ->
                    if (boolean == true) {
                        binding.pbEyeshadow.visibility = View.VISIBLE
                    } else {
                        binding.pbEyeshadow.visibility = View.GONE
                    }

                }
            }
        }
    }

    private fun setupRecyclerView() {
        eyeshadowAdapter = MainFeedAdapter(object : WebViewOnClickInterface {
            override fun goWebViewPage(productLink: String) {
                webSiteRotate(productLink)
            }
        }, object : ShopOnClickInterface {
            override fun shopProduct(product: ProductFeatures) {
                insertData(product)
            }
        })

        binding.apply {
            rvEyeshadow.adapter = eyeshadowAdapter
            rvEyeshadow.layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    private fun insertData(product: ProductFeatures) {
        viewModel.handleEvent(MainFeedEvent.SaveMainToLocalDatabase(product))
    }

    private fun webSiteRotate(productLink: String) {
        val bundle = Bundle().apply {
            putSerializable("weblink", productLink)
        }
        findNavController().navigate(R.id.action_mainFeedFragment_to_webViewFragment, bundle)
    }
}