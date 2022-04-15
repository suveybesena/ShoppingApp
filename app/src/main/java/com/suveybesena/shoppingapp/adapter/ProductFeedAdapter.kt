package com.suveybesena.shoppingapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.suveybesena.shoppingapp.R
import com.suveybesena.shoppingapp.data.remote.model.MakeupItemResponseItem
import com.suveybesena.shoppingapp.presentation.productfeed.mainfeed.OnClickButtonInterface
import kotlinx.android.synthetic.main.shopping_item.view.*

class ProductFeedAdapter (val onClickButtonInterface: OnClickButtonInterface) : RecyclerView.Adapter<ProductFeedAdapter.ProductVH>() {
    class ProductVH(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallBack = object : DiffUtil.ItemCallback<MakeupItemResponseItem>() {
        override fun areItemsTheSame(
            oldItem: MakeupItemResponseItem,
            newItem: MakeupItemResponseItem
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: MakeupItemResponseItem,
            newItem: MakeupItemResponseItem
        ): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallBack)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductVH {
        val layoutInflater =
            LayoutInflater.from(parent.context).inflate(R.layout.shopping_item, parent, false)
        return ProductVH(layoutInflater)
    }

    override fun onBindViewHolder(holder: ProductVH, position: Int) {
        val product = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(product.imageLink).into(ivProduct)
            tvProductName.text = product.brand
            tvProductPrice.text = "$ ${product.price}"

            bvAdd.setOnClickListener {
                product.productLink?.let { it1 ->
                    product.id?.let { it2 ->
                        onClickButtonInterface.onClick(bvAdd ,bvShop,bvWebsite,
                            product
                        )
                    }
                }
            }

        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}
