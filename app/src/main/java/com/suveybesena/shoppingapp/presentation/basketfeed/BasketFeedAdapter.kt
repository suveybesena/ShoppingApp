package com.suveybesena.shoppingapp.presentation.basketfeed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.suveybesena.shoppingapp.common.downLoadImage
import com.suveybesena.shoppingapp.data.model.ProductFeatures
import com.suveybesena.shoppingapp.databinding.BasketItemBinding

class BasketFeedAdapter() : RecyclerView.Adapter<BasketFeedAdapter.BasketVH>() {
    class BasketVH(val binding: BasketItemBinding) : RecyclerView.ViewHolder(binding.root)

    private var differCallBack = object : DiffUtil.ItemCallback<ProductFeatures>() {
        override fun areItemsTheSame(
            oldItem: ProductFeatures,
            newItem: ProductFeatures
        ): Boolean {
            return true
        }

        override fun areContentsTheSame(
            oldItem: ProductFeatures,
            newItem: ProductFeatures
        ): Boolean {
            return true
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketVH {
        val binding = BasketItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BasketVH(binding)
    }

    override fun onBindViewHolder(holder: BasketVH, position: Int) {
        val basketItem = differ.currentList[position]

        holder.binding.apply {
            basketItem.productImage?.let { url ->
                ivBasketItem.downLoadImage(url)
            }
            tvProductDesc.text = basketItem.productName
            tvProductName.text = basketItem.productCategory
            tvProductPrice.text = "$ ${basketItem.productPrice}"
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}