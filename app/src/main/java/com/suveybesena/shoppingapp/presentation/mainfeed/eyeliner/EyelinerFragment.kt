package com.suveybesena.shoppingapp.presentation.mainfeed.eyeliner

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
import com.suveybesena.shoppingapp.databinding.FragmentEyelinerBinding
import com.suveybesena.shoppingapp.presentation.mainfeed.MainFeedAdapter
import com.suveybesena.shoppingapp.presentation.mainfeed.MainFeedEvent
import com.suveybesena.shoppingapp.presentation.mainfeed.ShopOnClickInterface
import com.suveybesena.shoppingapp.presentation.mainfeed.WebViewOnClickInterface
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EyelinerFragment : Fragment() {

    private val viewModel: EyelinerViewModel by viewModels()
    private lateinit var eyelinerAdapter: MainFeedAdapter
    private lateinit var binding: FragmentEyelinerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEyelinerBinding.inflate(inflater)
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
                state.isLoading.let { boolean ->
                    if (!boolean) {
                        binding.pbEyeliner.visibility = View.GONE
                    } else {
                        binding.pbEyeliner.visibility = View.VISIBLE
                    }
                }
                state.eyelinerItems.let { eyelinerItemsList ->
                    eyelinerAdapter.differ.submitList(eyelinerItemsList)
                }
            }
        }
    }

    private fun setupRecyclerView() {
        eyelinerAdapter = MainFeedAdapter(object : WebViewOnClickInterface {
            override fun goWebViewPage(productLink: String) {
                websiteRotate(productLink)
            }

        }, object : ShopOnClickInterface {
            override fun shopProduct(product: ProductFeatures) {
                saveProducts(product)
            }
        })
        binding.apply {
            rvEyeliner.adapter = eyelinerAdapter
            rvEyeliner.layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    private fun saveProducts(product: ProductFeatures) {
        viewModel.handleEvent(MainFeedEvent.SaveMainToLocalDatabase(product))
    }

    private fun websiteRotate(
        productLink: String
    ) {
        val bundle = Bundle().apply {
            putSerializable("weblink", productLink)
        }
        findNavController().navigate(R.id.action_mainFeedFragment_to_webViewFragment, bundle)
    }
}