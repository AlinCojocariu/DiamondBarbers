package com.example.diamondbarbers.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.diamondbarbers.R
import com.example.diamondbarbers.activities.ReservationDialog
import com.example.diamondbarbers.Services

class ServiceAdapter(var context: Context, private val arrayList: List<Services>, var date: String, var hour: String, var hairStylist: String): RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ServiceViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.card_service, parent, false)
        return ServiceViewHolder(view)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val service = arrayList[position]

        holder.price.text = service.price
        holder.time.text = service.time
        holder.serviceName.text = service.type

        holder.itemView.setOnClickListener {
            val dialog = ReservationDialog(date, hour, hairStylist, service)
            dialog.show((context as AppCompatActivity).supportFragmentManager , "Reservation details")
        }
    }

    inner class ServiceViewHolder(myView: View): RecyclerView.ViewHolder(myView) {

        var price: TextView = myView.findViewById(R.id.price)
        var time: TextView = myView.findViewById(R.id.time)
        var serviceName: TextView = myView.findViewById(R.id.service_name)
    }
}