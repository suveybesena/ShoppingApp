package com.suveybesena.shoppingapp.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.suveybesena.shoppingapp.R
import com.suveybesena.shoppingapp.common.downLoadImage
import com.suveybesena.shoppingapp.presentation.productfeed.main.ProductFeatures
import kotlinx.android.synthetic.main.basket_item.view.*

class BasketAdapter() : RecyclerView.Adapter<BasketAdapter.BasketVH>() {
    class BasketVH(itemView: View) : RecyclerView.ViewHolder(itemView)

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
        return BasketVH(
            LayoutInflater.from(parent.context).inflate(R.layout.basket_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BasketVH, position: Int) {
        val basketItems = differ.currentList[position]
        holder.itemView.apply {

            basketItems.productImage?.let { url->
                ivBasketItem.downLoadImage(url)
            }
            Glide.with(this).load(basketItems.productImage).into(ivBasketItem)
            tvProductDesc.text = basketItems.productName
            tvProductName.text = basketItems.productCategory
            tvProductPrice.text = "$ ${basketItems.productPrice}"
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}