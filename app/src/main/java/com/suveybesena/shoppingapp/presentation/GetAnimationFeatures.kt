package com.suveybesena.shoppingapp.presentation

import android.content.Context
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.suveybesena.shoppingapp.R

class GetAnimationFeatures(context: Context?) {
    val rotateOpen: Animation by lazy {
        AnimationUtils.loadAnimation(
            context,
            R.anim.rotate_open_anim
        )
    }
    val rotateClose: Animation by lazy {
        AnimationUtils.loadAnimation(
            context,
            R.anim.rotate_close_anim
        )
    }
    val fromBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            context,
            R.anim.from_bottom_anim
        )
    }
    val toBottom: Animation by lazy { AnimationUtils.loadAnimation(context, R.anim.to_bottom_anim) }
}