package com.app.revolutapp.helpers

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.app.revolutapp.utils.StringContract.DRAWABLE
import com.app.revolutapp.utils.StringContract.ICON_PREFIX
import com.app.revolutapp.utils.StringContract.LABEL_SUFFIX
import com.app.revolutapp.utils.StringContract.STRING
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * Created by mayank on January 22 2020
 */

@BindingAdapter("imageFlag")
fun loadImage(view: ImageView, currency: String) {
    val flagIconResId = view.resources.getIdentifier(
        ICON_PREFIX
                + currency.toLowerCase(), DRAWABLE, view.context.packageName
    )
    Glide.with(view.context)
        .load(flagIconResId).apply(RequestOptions().circleCrop())
        .into(view)
}

@BindingAdapter("countryName")
fun displayCountry(view: TextView, currency: String) {
    val countryNameResId = view.resources.getIdentifier(
        currency.toLowerCase()
                + LABEL_SUFFIX, STRING, view.context.packageName
    )
    view.setText(countryNameResId)
}