package com.suveybesena.shoppingapp.presentation.mainfeed

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.suveybesena.shoppingapp.R
import com.suveybesena.shoppingapp.common.downLoadImage
import com.suveybesena.shoppingapp.data.model.ProductFeatures
import com.suveybesena.shoppingapp.databinding.ShoppingItemBinding

class MainFeedAdapter(
    private val webViewOnClickInterface: WebViewOnClickInterface,
    private val shopOnClickInterface: ShopOnClickInterface
) :
    RecyclerView.Adapter<MainFeedAdapter.ProductVH>() {

    class ProductVH(val binding: ShoppingItemBinding) : RecyclerView.ViewHolder(binding.root)

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
        val binding =
            ShoppingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductVH(binding)
    }

    override fun onBindViewHolder(holder: ProductVH, position: Int) {
        val rotateOpen: Animation by lazy {
            AnimationUtils.loadAnimation(
                holder.itemView.context,
                R.anim.rotate_open_anim
            )
        }
        val rotateClose: Animation by lazy {
            AnimationUtils.loadAnimation(
                holder.itemView.context,
                R.anim.rotate_close_anim
            )
        }
        val fromBottom: Animation by lazy {
            AnimationUtils.loadAnimation(
                holder.itemView.context,
                R.anim.from_bottom_anim
            )
        }
        val toBottom: Animation by lazy {
            AnimationUtils.loadAnimation(
                holder.itemView.context,
                R.anim.to_bottom_anim
            )
        }

        val mainFeedItem = differ.currentList[position]
        holder.binding.apply {
            mainFeedItem.productImage?.let { url ->
                ivProduct.downLoadImage(url)
            }
            tvProductName.text = mainFeedItem.productName
            tvProductPrice.text = "$ ${mainFeedItem.productPrice}"
            val clicked = false

            bvAdd.setOnClickListener {
                if (!clicked) {
                    bvAdd.startAnimation(rotateOpen)
                    bvShop.startAnimation(fromBottom)
                    bvWebsite.startAnimation(fromBottom)
                } else {
                    bvAdd.startAnimation(rotateClose)
                    bvShop.startAnimation(toBottom)
                    bvWebsite.startAnimation(toBottom)
                }
            }
            bvWebsite.setOnClickListener {
                mainFeedItem.productLink?.let { productLink ->
                    webViewOnClickInterface.goWebViewPage(productLink)
                }
            }
            bvShop.setOnClickListener {
                mainFeedItem?.let { product ->
                    shopOnClickInterface.shopProduct(product)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}
