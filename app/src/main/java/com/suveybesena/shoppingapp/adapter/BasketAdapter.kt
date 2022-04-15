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
import kotlinx.android.synthetic.main.basket_item.view.*

class BasketAdapter():RecyclerView.Adapter<BasketAdapter.BasketVH>() {
    class BasketVH (itemView : View) :RecyclerView.ViewHolder(itemView)


    private var differCallBack = object : DiffUtil.ItemCallback<MakeupItemResponseItem>(){
        override fun areItemsTheSame(
            oldItem: MakeupItemResponseItem,
            newItem: MakeupItemResponseItem
        ): Boolean {
            return true
        }

        override fun areContentsTheSame(
            oldItem: MakeupItemResponseItem,
            newItem: MakeupItemResponseItem
        ): Boolean {
            return true     }

    }

     val differ = AsyncListDiffer(this,differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketVH {
        return BasketVH(
            LayoutInflater.from(parent.context).inflate(R.layout.basket_item,parent,false)
        )
    }

    override fun onBindViewHolder(holder: BasketVH, position: Int) {
        val basketItems = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(basketItems.imageLink).into(ivBasketItem)
            tvProductDesc.text = basketItems.brand
            tvProductName.text = basketItems.name
            tvProductPrice.text ="$ ${basketItems.price}"


        }
    }

    override fun getItemCount(): Int {
       return differ.currentList.size
    }
}