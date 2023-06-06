package com.example.diamondbarbers.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.example.diamondbarbers.*
import com.example.diamondbarbers.models.Appointment
import com.example.diamondbarbers.models.Service
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ReservationDialog(var date: String, var hour: String, var hairStylist: String, var service: Service): DialogFragment() {


    private lateinit var userName:String
    private lateinit var userPhone:String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_appointment_details, container, false)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)

        val date = view.findViewById<TextView>(R.id.date)
        val hour = view.findViewById<TextView>(R.id.hour)
        val hairStylist = view.findViewById<TextView>(R.id.hairstylist)
        val price = view.findViewById<TextView>(R.id.price)
        val service = view.findViewById<TextView>(R.id.service)

        val reservationButton = view.findViewById<Button>(R.id.reservation_button)

        date.text = this.date
        hour.text = this.hour
        hairStylist.text = this.hairStylist
        price.text = this.service.price
        service.text = this.service.type

       val userInfo = UserInformation.userInfo
        userName = userInfo!!.name
        userPhone = userInfo!!.phone

        if(UserInformation.currentBarberShop != null && UserInformation.currentHairStylist != null) {
            val barberShopListRef = FirebaseDatabase.getInstance().getReference("barber-shops")
            val barberShopRef = barberShopListRef.child("${UserInformation.currentBarberShop!!.id}")
            val hairstylistListRef = barberShopRef.child("hairstylists")
            val appointmentsRef = hairstylistListRef.child("${UserInformation.currentHairStylist!!.id}").child("programari")


            reservationButton.setOnClickListener {
                Toast.makeText(context, "Rezervat cu succes", Toast.LENGTH_SHORT).show()

                val updateReservation = Appointment(
                    date.text.toString(),
                    hour.text.toString(),
                    userName,
                    userPhone,
                    service.text.toString()
                )
                appointmentsRef.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val currentArray = dataSnapshot.value as MutableList<Appointment>
                        currentArray.add(updateReservation)

                        appointmentsRef.setValue(currentArray)

                        // Update current hairstylist
                        val newAppointmentsList = UserInformation.currentHairStylist!!.appointments.toMutableList()
                        newAppointmentsList.add(updateReservation)
                        UserInformation.currentHairStylist!!.appointments = newAppointmentsList

                        (context as AppCompatActivity).finish()
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        // Handle the error
                    }
                })
            }

        }

        return view
    }
}