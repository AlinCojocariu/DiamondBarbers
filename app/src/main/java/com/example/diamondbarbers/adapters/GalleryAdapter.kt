package com.example.diamondbarbers.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.example.diamondbarbers.GlideAppModule
import com.example.diamondbarbers.R

class GalleryAdapter(private val context: Context, private val imageList: List<String>) : PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val slideLayout = inflater.inflate(R.layout.card_gallery, container, false)

        val slideImage = slideLayout.findViewById<ImageView>(R.id.slideImage)

        GlideAppModule.loadImage(context, imageList[position], slideImage)

        container.addView(slideLayout)
        return slideLayout
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }

    override fun getCount(): Int {
        return imageList.size
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }
}
