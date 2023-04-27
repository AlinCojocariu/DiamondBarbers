package com.example.diamondbarbers

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView



import androidx.recyclerview.widget.RecyclerView

class FrizerieAdapter(private val context: Context, private val frizerieList:ArrayList<Frizerie>): RecyclerView.Adapter<FrizerieAdapter.FrizerieViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FrizerieAdapter.FrizerieViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.design_recycleview_frizerii, parent, false)
        return FrizerieViewHolder(view)
    }

    override fun onBindViewHolder(holder: FrizerieAdapter.FrizerieViewHolder, position: Int) {
        val frizerie = frizerieList[position]

        holder.nume.text=frizerie.nume
        holder.adresa.text=frizerie.adresa

        // Add click listener to the item view
        holder.itemView.setOnClickListener {
            val intent = Intent (context,HairStylistsActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable("barbershop",frizerie)
            intent.putExtras(bundle)

            intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK

            context.startActivity(intent)
        }


    }

    override fun getItemCount(): Int {
        return frizerieList.size
    }

    class FrizerieViewHolder(myView: View):RecyclerView.ViewHolder(myView){

        var nume:TextView=myView.findViewById(R.id.numeFrizerie)
        var adresa:TextView=myView.findViewById(R.id.adresaFrizerie)
        var location:ImageView=myView.findViewById(R.id.locationpin)
    }
}