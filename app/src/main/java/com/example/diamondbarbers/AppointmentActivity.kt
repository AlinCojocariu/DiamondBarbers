package com.example.diamondbarbers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AppointmentActivity : AppCompatActivity() {

    private lateinit var hairStylist : HairStylist
    private lateinit var hour: String
    private lateinit var date: String

    private lateinit var calendarTime: TextView
    private lateinit var hairStylistName: TextView
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointment)

        calendarTime = findViewById(R.id.calendar_time)
        hairStylistName = findViewById(R.id.hairstylist_name)
        recyclerView = findViewById(R.id.recycler_view)

        val bundle = intent.extras
        if(bundle != null) {
            hairStylist = bundle.getParcelable("hairstylist")!!
            hour = bundle.getString("hour")!!
            date = bundle.getString("date")!!

            calendarTime.text = "$date - $hour"
            hairStylistName.text = hairStylist.name

            recyclerView.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = ServiceAdapter(this, hairStylist.services, date, hour, hairStylist.name)
        } else {
            Toast.makeText(applicationContext, "Datele despre frizerie nu au fost primite", Toast.LENGTH_SHORT).show()
        }
    }


}