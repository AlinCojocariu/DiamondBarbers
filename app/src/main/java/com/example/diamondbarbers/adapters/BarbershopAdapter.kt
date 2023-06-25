package com.example.diamondbarbers.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView



import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.diamondbarbers.models.Barbershop
import com.example.diamondbarbers.activities.HairstylistsActivity
import com.example.diamondbarbers.R
import com.example.diamondbarbers.UserInformation

class BarbershopAdapter(private val context: Context, private val barbershopList:ArrayList<Barbershop>): RecyclerView.Adapter<BarbershopAdapter.BarbershopViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BarbershopViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.card_barbershop, parent, false)
        return BarbershopViewHolder(view)
    }

    override fun onBindViewHolder(holder: BarbershopViewHolder, position: Int) {
        val barbershop = barbershopList[position]

        holder.name.text=barbershop.name
        holder.address.text=barbershop.address
        holder.city.text=barbershop.city
        holder.location.setOnClickListener {
            val query = "${barbershop.name}, ${barbershop.address}, ${barbershop.city}"
            val gmmIntentUri = Uri.parse("geo:0,0?q=$query")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            holder.itemView.context.startActivity(mapIntent)
        }

        val galleryAdapter = GalleryAdapter(context, barbershop.barbershopGallery)
        holder.barbershopPhotos.adapter = galleryAdapter

        // Add click listener to the item view
        holder.itemView.setOnClickListener {


            val intent = Intent (context, HairstylistsActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable("barbershop",barbershop)
            intent.putExtras(bundle)

            intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK

            UserInformation.currentBarberShop = barbershop
            context.startActivity(intent)
        }


    }

    override fun getItemCount(): Int {
        return barbershopList.size
    }

    class BarbershopViewHolder(myView: View):RecyclerView.ViewHolder(myView){

        var name:TextView=myView.findViewById(R.id.barbershop_name)
        var address:TextView=myView.findViewById(R.id.barbershop_address)
        var city:TextView=myView.findViewById(R.id.barbershop_city)
        var location:ImageView =myView.findViewById(R.id.location_pin)
        val barbershopPhotos: ViewPager = itemView.findViewById(R.id.barbershop_photos)

    }
}