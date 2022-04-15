package com.suveybesena.shoppingapp.presentation.productfeed.eyeliner

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
import com.suveybesena.shoppingapp.databinding.FragmentEyelinerBinding
import com.suveybesena.shoppingapp.adapter.ProductFeedAdapter
import com.suveybesena.shoppingapp.data.remote.model.MakeupItemResponseItem
import com.suveybesena.shoppingapp.presentation.productfeed.mainfeed.OnClickButtonInterface
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EyelinerFragment : Fragment() {

    private val rotateOpen : Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_open_anim) }
    private val rotateClose : Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.rotate_close_anim) }
    private val fromBottom : Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.from_bottom_anim) }
    private val toBottom : Animation by lazy { AnimationUtils.loadAnimation(requireContext(), R.anim.to_bottom_anim) }
    private var clicked = false

    private val viewModel: EyelinerViewModel by viewModels()
    private lateinit var eyelinerAdapter: ProductFeedAdapter
    private lateinit var binding: FragmentEyelinerBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEyelinerBinding.inflate(inflater)
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
                eyelinerAdapter.differ.submitList(state.productItems)
            }

        })
    }

    private fun setupRecyclerView() {
        eyelinerAdapter = ProductFeedAdapter(object : OnClickButtonInterface {
            override fun onClick(
                addButton: FloatingActionButton,
                shopButton: FloatingActionButton,
                websiteButton: FloatingActionButton,
                products : MakeupItemResponseItem
            ) {
                setMenuVisibility(clicked)
                insertData(shopButton, products)
                setAnimation(clicked,addButton, shopButton,websiteButton)
                clicked =!clicked
                products.productLink?.let { websiteRotate(websiteButton, it) }

            }


        })
        binding.apply {
            rvEyeliner.adapter = eyelinerAdapter
            rvEyeliner.layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    private fun insertData(addButton: FloatingActionButton, product: MakeupItemResponseItem) {
        addButton.setOnClickListener {
            viewModel.insertData(product)
        }

    }

    private fun websiteRotate(
        websiteButton: FloatingActionButton,
        products: String
    ) {
        websiteButton.setOnClickListener {
            val bundle = Bundle().apply {
                putSerializable("weblink",products)

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