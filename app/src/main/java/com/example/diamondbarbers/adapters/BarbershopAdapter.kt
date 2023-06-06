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
import com.example.diamondbarbers.models.Barbershop
import com.example.diamondbarbers.activities.HairstylistsActivity
import com.example.diamondbarbers.R
import com.example.diamondbarbers.UserInformation

class BarbershopAdapter(private val context: Context, private val barbershopList:ArrayList<Barbershop>): RecyclerView.Adapter<BarbershopAdapter.FrizerieViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FrizerieViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.card_barbershop, parent, false)
        return FrizerieViewHolder(view)
    }

    override fun onBindViewHolder(holder: FrizerieViewHolder, position: Int) {
        val frizerie = barbershopList[position]

        holder.name.text=frizerie.name
        holder.address.text=frizerie.address
        holder.city.text=frizerie.city
        holder.location.setOnClickListener {
            val query = "${frizerie.name}, ${frizerie.address}, ${frizerie.city}"
            val gmmIntentUri = Uri.parse("geo:0,0?q=$query")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            holder.itemView.context.startActivity(mapIntent)
        }

        // Add click listener to the item view
        holder.itemView.setOnClickListener {


            val intent = Intent (context, HairstylistsActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable("barbershop",frizerie)
            intent.putExtras(bundle)

            intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK

            UserInformation.currentBarberShop = frizerie
            context.startActivity(intent)
        }


    }

    override fun getItemCount(): Int {
        return barbershopList.size
    }

    class FrizerieViewHolder(myView: View):RecyclerView.ViewHolder(myView){

        var name:TextView=myView.findViewById(R.id.numeFrizerie)
        var address:TextView=myView.findViewById(R.id.adresaFrizerie)
        var city:TextView=myView.findViewById(R.id.orasFrizerie)
        var location:ImageView =myView.findViewById(R.id.locationpin)


    }
}