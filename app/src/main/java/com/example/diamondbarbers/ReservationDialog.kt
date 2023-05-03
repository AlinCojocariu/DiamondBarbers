package com.example.diamondbarbers

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment

class ReservationDialog(var date: String, var hour: String, var hairStylist: String, var service: Services): DialogFragment() {

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

        reservationButton.setOnClickListener {
            Toast.makeText(context, "Rezervat cu succes", Toast.LENGTH_SHORT).show()
            (context as AppCompatActivity).finish()
        }

        return view
    }
}