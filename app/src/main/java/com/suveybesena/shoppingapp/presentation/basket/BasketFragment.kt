package com.suveybesena.shoppingapp.presentation.basket

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.suveybesena.shoppingapp.adapter.BasketAdapter
import com.suveybesena.shoppingapp.databinding.FragmentBasketBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_basket.*
import kotlinx.android.synthetic.main.fragment_basket.view.*

@AndroidEntryPoint
class BasketFragment : Fragment() {

    private val viewModel: BasketViewModel by viewModels()
    private lateinit var binding: FragmentBasketBinding
    private lateinit var basketAdapter: BasketAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBasketBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeData()
    }
    private fun observeData() {
        viewModel.getAllProducts()
        viewModel._uiState.observe(viewLifecycleOwner, Observer { state ->
            if (state != null) {

                basketAdapter.differ.submitList(state.basketFeedItems)
               state.basketFeedItems
            }
            val eachItem = state.basketFeedItems
            if (eachItem != null) {
                var a = 0
                eachItem.forEach { x->
                    val price = x.price?.toDouble()
                    price?.let { it->
                         a= (a+it).toInt()
                        tvSubTotal.text = a.toString()
                    }
                }
                binding.tvSubTotal.text = "Subtotal : $${a.toString()}"
                println("Subtotal : $${a.toString()}")
            }
        })
    }

    private fun setupRecyclerView() {
        basketAdapter = BasketAdapter()
        binding.rvBasket.apply {
            rvBasket.adapter = basketAdapter
        }
    }

}