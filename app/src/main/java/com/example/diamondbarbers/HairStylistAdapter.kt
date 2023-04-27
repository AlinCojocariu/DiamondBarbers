package com.example.diamondbarbers

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class HairStylistAdapter(private val context:Context, private val hairStylistList:List<HairStylist>):RecyclerView.Adapter<HairStylistAdapter.ImageViewHolder>() {

    class ImageViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val image: ImageView = itemView.findViewById(R.id.hairstylist_image)
        val name: TextView = itemView.findViewById(R.id.hairstylist_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.design_recyclerview_hairstylists,parent,false)
        return ImageViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val hairStylist = hairStylistList[position]

        // Setup UI
        GlideAppModule.loadImage(context, hairStylist.image, holder.image)
        holder.name.text = hairStylist.name

        // Handle item click
        holder.itemView.setOnClickListener {

        }


        if (context != null && holder.itemView.context != null) {
            // Load image with Glide using the valid context
            GlideAppModule.loadImage(context, hairStylist.image, holder.image)
           // GlideAppModule.loadImage(context, imageurl.url, holder.image2)

        } else {
            // Handle the case where the context is null or not valid
            Log.e("HairStylistAdapter", "Invalid context")
        }

    }

    override fun getItemCount(): Int {
        return hairStylistList.size
    }


}