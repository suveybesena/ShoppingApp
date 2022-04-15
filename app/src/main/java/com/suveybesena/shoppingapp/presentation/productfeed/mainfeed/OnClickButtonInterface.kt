package com.suveybesena.shoppingapp.presentation.productfeed.mainfeed

import android.widget.Button
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.suveybesena.shoppingapp.data.remote.model.MakeupItemResponseItem

interface OnClickButtonInterface {
    fun onClick(
        addButton: FloatingActionButton,
        shopButton: FloatingActionButton,
        websiteButton: FloatingActionButton,
        product : MakeupItemResponseItem

    )
}