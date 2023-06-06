package com.example.diamondbarbers.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diamondbarbers.Products
import com.example.diamondbarbers.R

class HairstylistProductsAdapter(private val context: Context, private val productsList: List<Products>):RecyclerView.Adapter<HairstylistProductsAdapter.HairstylistProductsViewHolder>() {

    inner class HairstylistProductsViewHolder(myView: View):RecyclerView.ViewHolder(myView){
        val name:TextView=myView.findViewById(R.id.productName)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HairstylistProductsViewHolder {
         val itemView=LayoutInflater.from(parent.context).inflate(R.layout.card_products,parent,false)
        return HairstylistProductsViewHolder(itemView)

    }

    override fun getItemCount(): Int {
        return productsList.size
    }

    override fun onBindViewHolder(holder: HairstylistProductsViewHolder, position: Int) {
        val product=productsList[position]

        holder.name.text=product.productName
    }
}