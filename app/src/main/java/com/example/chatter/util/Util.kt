package com.example.chatter.util

import android.content.Context
import android.graphics.drawable.AdaptiveIconDrawable
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.chatter.R

fun ImageView.loadUrl(url:String?,errorDrawable: Int= R.drawable.empty) {
    context?.let {
        val options=RequestOptions()
            .placeholder(progressDrawable(context))
            .error(errorDrawable)
        Glide.with(context.applicationContext)
            .load(url)
            .apply(options)
            .into(this)
    }
}
fun progressDrawable(context: Context):CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth=5f
        centerRadius=30f
        start()
    }
}