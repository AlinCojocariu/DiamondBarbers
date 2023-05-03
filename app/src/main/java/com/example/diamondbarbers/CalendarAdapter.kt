package com.example.diamondbarbers

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CalendarAdapter(private val context: Context, private val arrayList: ArrayList<CalendarSchedule>, var hairStylist: HairStylist): RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CalendarViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.card_calendar_schedule, parent, false)
        return CalendarViewHolder(view)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: CalendarAdapter.CalendarViewHolder, position: Int) {
        val schedule = arrayList[position]

        if(schedule.reserved) {
            holder.scheduleStatus.text = "Rezervat"
        } else {
            holder.scheduleStatus.text = "Liber"
        }

        holder.scheduleHour.text = schedule.appointment.hour

        holder.itemView.setOnClickListener {
            val intent = Intent (context,AppointmentActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable("hairstylist", hairStylist)
            bundle.putString("hour", schedule.appointment.hour)
            bundle.putString("date", schedule.appointment.date)
            intent.putExtras(bundle)

            intent.flags= Intent.FLAG_ACTIVITY_NEW_TASK

            context.startActivity(intent)
        }
    }

    inner class CalendarViewHolder(myView: View): RecyclerView.ViewHolder(myView) {

        var scheduleHour: TextView = myView.findViewById(R.id.schedule_hour)
        var scheduleStatus: TextView = myView.findViewById(R.id.schedule_status)
    }
}