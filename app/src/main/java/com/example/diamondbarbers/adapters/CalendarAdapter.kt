package com.example.diamondbarbers.adapters

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.diamondbarbers.*
import com.example.diamondbarbers.activities.AppointmentActivity
import com.example.diamondbarbers.models.Appointment
import com.example.diamondbarbers.models.CalendarSchedule
import com.example.diamondbarbers.models.HairStylist
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

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

    private fun showCancelAppointmentDialog(context: Context,holder:ViewHolder) {

            val dialog = Dialog(context)
            dialog.setContentView(R.layout.dialog_cancel_reservation)

            val titleTextView: TextView = dialog.findViewById(R.id.dialog_title)
            val messageTextView: TextView = dialog.findViewById(R.id.dialog_message)
            val yesButton: Button = dialog.findViewById(R.id.dialog_yes_button)
            val noButton: Button = dialog.findViewById(R.id.dialog_no_button)

            titleTextView.text = "Anulare Programare"
            messageTextView.text = "Vrei sa anulezi programarea?"

            yesButton.setOnClickListener {
                Toast.makeText(context, "ai anulat cu succes", Toast.LENGTH_SHORT).show()

//                val databaseRef= FirebaseDatabase.getInstance().reference
//                val barbershopListRef = databaseRef.child("barbershops")
//                val barbershopRef = barbershopListRef.child("${UserInformation.currentBarberShop!!.id}")
//                val hairstylistListRef =barbershopRef.child("hairstylists")
//                val appointmentRef =hairstylistListRef.child("${UserInformation.currentHairStylist!!.id}").child("appointments")
//
//                appointmentRef.addListenerForSingleValueEvent(object : ValueEventListener {
//                    override fun onDataChange(appointmentsnapshot: DataSnapshot) {
//                        val appointmentsList = appointmentsnapshot.children.toMutableList()
//                       for((index,appointment) in appointmentsList.withIndex()){
//                           val appointmentCriteria=appointment.getValue(Appointment::class.java)
//
//                           if(appointmentCriteria!!.name==UserInformation.userInfo!!.name
//                                                        &&
//                               appointmentCriteria.phone==UserInformation.userInfo!!.phone)
//                           {
//                               val appointmentErased = appointment.ref
//                               appointmentErased.removeValue().addOnCompleteListener{
//
//                                   Toast.makeText(context, "Programarea a fost stearsa cu succes", Toast.LENGTH_SHORT).show()
//
//                               }.addOnFailureListener{
//
//                                   Toast.makeText(context, "Programarea nu a fost stearsa", Toast.LENGTH_SHORT).show()
//                               }
//                               appointmentsList.removeAt(index)
//                               break
//                           }
//
//                       }
//                        appointmentRef.setValue(appointmentsList)
//
//                    }
//
//                    override fun onCancelled(error: DatabaseError) {
//                        Toast.makeText(context, "Nu s-a realizat stergerea", Toast.LENGTH_SHORT).show()
//                    }
//
//
//                })

                holder.itemView.setBackgroundColor(Color.WHITE)

                dialog.dismiss()
            }

            noButton.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
    }



    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val schedule = arrayList[position]

        if(schedule.reserved) {
            holder.scheduleStatus.text = "Rezervat"

            if(UserInformation.userInfo != null) {
                if (schedule.appointment.phone == UserInformation.userInfo!!.phone) {
                    val color = ContextCompat.getColor(context, R.color.rose)
                    holder.itemView.setBackgroundColor(color)
                }
            }
        } else {
            holder.scheduleStatus.text = "Liber"
        }

        holder.scheduleHour.text = schedule.appointment.hour

        holder.itemView.setOnClickListener {
            if(!schedule.reserved) {
                val intent = Intent(context, AppointmentActivity::class.java)
                val bundle = Bundle()
                bundle.putParcelable("hairstylist", hairStylist)
                bundle.putString("hour", schedule.appointment.hour)
                bundle.putString("date", schedule.appointment.date)
                intent.putExtras(bundle)

                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

                context.startActivity(intent)
            } else {
                if(UserInformation.userInfo != null) {
                    if (schedule.appointment.phone == UserInformation.userInfo!!.phone) {
                        // sterge rezervare
                        showCancelAppointmentDialog(holder.itemView.context,holder)

                    }
                }
            }
        }
    }

    inner class CalendarViewHolder(myView: View): RecyclerView.ViewHolder(myView) {

        var scheduleHour: TextView = myView.findViewById(R.id.schedule_hour)
        var scheduleStatus: TextView = myView.findViewById(R.id.schedule_status)
    }



}