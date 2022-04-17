package com.suveybesena.shoppingapp.presentation.mainfeed.lipstick

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.suveybesena.shoppingapp.R
import com.suveybesena.shoppingapp.databinding.FragmentLipstickBinding
import com.suveybesena.shoppingapp.presentation.mainfeed.MainFeedAdapter
import com.suveybesena.shoppingapp.data.model.ProductFeatures
import com.suveybesena.shoppingapp.presentation.mainfeed.MainFeedEvent
import com.suveybesena.shoppingapp.presentation.mainfeed.ShopOnClickInterface
import com.suveybesena.shoppingapp.presentation.mainfeed.WebViewOnClickInterface
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LipstickFragment : Fragment() {

    private val viewModel: LipstickViewModel by viewModels()
    private lateinit var binding: FragmentLipstickBinding
    lateinit var mainFeedAdapter: MainFeedAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLipstickBinding.inflate(inflater)
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
            viewModel.uiState.collect { uiState ->
                uiState.lipstickItems.let { lipstickItemsList ->
                    mainFeedAdapter.differ.submitList(lipstickItemsList)
                }
                uiState.isLoading.let { boolean ->
                    if (!boolean){
                        binding.pbLipstick.visibility = View.GONE
                    }else   {
                        binding.pbLipstick.visibility = View.VISIBLE
                    }

                }
            }
        }
    }

    private fun setupRecyclerView() {
        mainFeedAdapter = MainFeedAdapter(object : WebViewOnClickInterface {
            override fun goWebViewPage(productLink: String) {
                webSiteRotate(productLink)
            }
        }, object : ShopOnClickInterface {
            override fun shopProduct(product: ProductFeatures) {
                saveProduct(product)
            }
        })
        binding.apply {
            rvLipstick.adapter = mainFeedAdapter
            rvLipstick.layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    private fun saveProduct(product: ProductFeatures) {
        viewModel.handleEvent(MainFeedEvent.SaveMainToLocalDatabase(product))
    }

    private fun webSiteRotate(productLink: String) {
        val bundle = Bundle().apply {
            putSerializable("weblink", productLink)
        }
        goToProductWebsite(bundle)
    }

    private fun goToProductWebsite(bundle: Bundle) {
        findNavController().navigate(R.id.action_mainFeedFragment_to_webViewFragment, bundle)
    }
}