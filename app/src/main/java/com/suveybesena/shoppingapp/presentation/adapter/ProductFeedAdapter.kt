package com.suveybesena.shoppingapp.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.suveybesena.shoppingapp.R
import com.suveybesena.shoppingapp.common.downLoadImage
import com.suveybesena.shoppingapp.presentation.GetAnimationFeatures
import com.suveybesena.shoppingapp.presentation.productfeed.main.ProductFeatures
import com.suveybesena.shoppingapp.presentation.productfeed.main.ShopOnClickInterface
import com.suveybesena.shoppingapp.presentation.productfeed.main.WebViewOnClickInterface
import kotlinx.android.synthetic.main.shopping_item.view.*

class ProductFeedAdapter(
    private val webViewOnClickInterface: WebViewOnClickInterface,
    private val shopOnClickInterface: ShopOnClickInterface
) :
    RecyclerView.Adapter<ProductFeedAdapter.ProductVH>() {
    class ProductVH(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallBack = object : DiffUtil.ItemCallback<ProductFeatures>() {
        override fun areItemsTheSame(
            oldItem: ProductFeatures,
            newItem: ProductFeatures
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: ProductFeatures,
            newItem: ProductFeatures
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
            product.productImage?.let { url->
                ivProduct.downLoadImage(url)
            }
            tvProductName.text = product.productName
            tvProductPrice.text = "$ ${product.productPrice}"
            val clicked = false

            bvAdd.setOnClickListener {
                if (!clicked) {
                    bvAdd.startAnimation(GetAnimationFeatures(context).rotateOpen)
                    bvShop.startAnimation(GetAnimationFeatures(context).fromBottom)
                    bvWebsite.startAnimation(GetAnimationFeatures(context).fromBottom)
                } else {
                    bvAdd.startAnimation(GetAnimationFeatures(context).rotateClose)
                    bvShop.startAnimation(GetAnimationFeatures(context).toBottom)
                    bvWebsite.startAnimation(GetAnimationFeatures(context).toBottom)
                }
            }
            bvWebsite.setOnClickListener {
                product.productLink?.let { productLink ->
                    webViewOnClickInterface.goWebViewPage(productLink)
                }
            }
            bvShop.setOnClickListener {
                product?.let { product ->
                    shopOnClickInterface.shopProduct(product)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}
