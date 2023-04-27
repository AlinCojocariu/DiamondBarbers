package com.example.diamondbarbers

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
@GlideModule
class GlideAppModule:AppGlideModule() {

    companion object {
        fun loadImage(context: Context, url: String?, imageView: ImageView) {
            Glide.with(context)
                .load(url)
                .error(R.drawable.ic_error_placeholder)
                .into(imageView)
        }
    }
}