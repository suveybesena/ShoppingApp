package com.suveybesena.shoppingapp.common

import android.widget.ImageView
import com.bumptech.glide.Glide
import java.lang.Exception

fun ImageView.downLoadImage(imageUrl : String){
   try {
       Glide.with(this.context).load(imageUrl).into(this)
   }catch (e:Exception){
       println(e.localizedMessage)
   }
}