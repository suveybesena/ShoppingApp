package com.suveybesena.shoppingapp.presentation.productfeed.eyeshadow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.suveybesena.shoppingapp.R
import com.suveybesena.shoppingapp.databinding.FragmentEyeshadowBinding
import com.suveybesena.shoppingapp.adapter.ProductFeedAdapter
import com.suveybesena.shoppingapp.data.remote.model.MakeupItemResponseItem
import com.suveybesena.shoppingapp.presentation.productfeed.mainfeed.OnClickButtonInterface
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EyeshadowFragment : Fragment() {

    private val rotateOpen : Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_open_anim) }
    private val rotateClose : Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_close_anim) }
    private val fromBottom : Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.from_bottom_anim) }
    private val toBottom : Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.to_bottom_anim) }
    private var clicked = false

    private val viewModel: EyeShadowViewModel by viewModels()
    lateinit var eyeshadowAdapter: ProductFeedAdapter
    private lateinit var binding: FragmentEyeshadowBinding


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
        viewModel._uiState.observe(viewLifecycleOwner, Observer { state ->
            if (state.productItems != null) {
                eyeshadowAdapter.differ.submitList(state.productItems)
            }

        })
    }

    private fun setupRecyclerView() {
        eyeshadowAdapter = ProductFeedAdapter(object : OnClickButtonInterface {
            override fun onClick(
                addButton: FloatingActionButton,
                shopButton: FloatingActionButton,
                websiteButton: FloatingActionButton,
                product : MakeupItemResponseItem

            ) {
                setMenuVisibility(clicked)
                insertData(shopButton, product)
                setAnimation(clicked, addButton,shopButton, websiteButton)
                clicked = !clicked
                product.productLink?.let { webSiteRotate(websiteButton, it) }
            }


        })
        binding.apply {
            rvEyeshadow.adapter = eyeshadowAdapter
            rvEyeshadow.layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    private fun insertData(addButton: FloatingActionButton, product: MakeupItemResponseItem) {
        addButton.setOnClickListener {
            viewModel.insertProduct(product)
        }
    }

    private fun webSiteRotate(websiteButton: FloatingActionButton, productLink: String) {
        websiteButton.setOnClickListener {
            val bundle = Bundle().apply {
                putSerializable("weblink", productLink)
            }
            findNavController().navigate(R.id.action_productFeedFragment_to_webViewFragment, bundle)

        }
    }

    private fun setAnimation(clicked: Boolean,
                             addButton: FloatingActionButton,
                             shopButton: FloatingActionButton,
                             websiteButton: FloatingActionButton) {
        if (!clicked){
            addButton.startAnimation(rotateOpen)
            shopButton.startAnimation(fromBottom)
            websiteButton.startAnimation(fromBottom)

        }else{
            addButton.startAnimation(rotateClose)
            shopButton.startAnimation(toBottom)
            websiteButton.startAnimation(toBottom)
        }
    }

}