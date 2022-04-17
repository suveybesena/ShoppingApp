package com.suveybesena.shoppingapp.presentation.basketfeed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.suveybesena.shoppingapp.databinding.FragmentBasketBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BasketFragment : Fragment() {

    private val viewModel: BasketViewModel by viewModels()
    private lateinit var binding: FragmentBasketBinding
    private lateinit var basketFeedAdapter: BasketFeedAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBasketBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observeData()
        initList()

    }

    private fun initList() {
        val itemTouchHelperCallBack = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val basketItem = basketFeedAdapter.differ.currentList[position]
                viewModel.handleEvent(BasketFeedEvent.DeleteProduct(basketItem))
                viewModel.handleEvent(BasketFeedEvent.GetProductsFromLocal)
                Snackbar.make(requireView(), "Sucessfully deleted.", Snackbar.LENGTH_LONG).apply {
                    setAction("Undo") {
                        viewModel.handleEvent(BasketFeedEvent.SaveProduct(basketItem))
                    }.show()
                }
            }
        }
        ItemTouchHelper(itemTouchHelperCallBack).apply {
            attachToRecyclerView(binding.rvBasket)
        }
    }

    private fun observeData() {
        viewModel.handleEvent(BasketFeedEvent.GetProductsFromLocal)
        lifecycleScope.launch {
            viewModel._uiState.collect { uiState ->
                uiState.basketFeedItems.let { savedItemList ->
                    basketFeedAdapter.differ.submitList(savedItemList)
                    var a = 0.0
                    savedItemList?.forEach { x ->
                        val price = x.productPrice?.toDouble()
                        price?.let { it ->
                            a = (a + it)
                            binding.tvSubTotal.text = a.toString()
                        }
                    }
                    binding.tvSubTotal.text = "Subtotal : $${a.toString()}"
                }
                uiState.isLoading.let { boolean ->
                    if (!boolean) {
                        binding.pgBasket.visibility = View.GONE
                    } else {
                        binding.pgBasket.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        basketFeedAdapter = BasketFeedAdapter()
        binding.rvBasket.apply {
            binding.rvBasket.adapter = basketFeedAdapter
        }
    }
}