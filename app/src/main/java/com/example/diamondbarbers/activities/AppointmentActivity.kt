package com.example.diamondbarbers.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diamondbarbers.models.GlideAppModule
import com.example.diamondbarbers.models.HairStylist
import com.example.diamondbarbers.R
import com.example.diamondbarbers.UserInformation
import com.example.diamondbarbers.adapters.ServiceAdapter

@Suppress("DEPRECATION")
class AppointmentActivity : AppCompatActivity() {

    private lateinit var hairStylist : HairStylist
    private lateinit var hour: String
    private lateinit var date: String
    private lateinit var hairstylistPictureUrl:String
    private lateinit var hairstylistImage:ImageView
    private lateinit var calendarTime: TextView
    private lateinit var hairStylistName: TextView
    private lateinit var userName:EditText
    private lateinit var userPhone:EditText
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointment)

        calendarTime = findViewById(R.id.calendar_time)
        hairStylistName = findViewById(R.id.hairstylist_name)
        hairstylistImage=findViewById(R.id.picture_hairstylist)
        userName=findViewById(R.id.client_name)
        userPhone=findViewById(R.id.client_phone)
        recyclerView = findViewById(R.id.recycler_view)

        val bundle = intent.extras
        if(bundle != null) {
            hairStylist = bundle.getParcelable("hairstylist")!!
            hour = bundle.getString("hour")!!
            date = bundle.getString("date")!!
            calendarTime.text = "$date - $hour"
            hairStylistName.text = hairStylist.name
            hairstylistPictureUrl=hairStylist.image
            val userInfo = UserInformation.userInfo
            userName.setText(userInfo!!.name)
            userPhone.setText(userInfo!!.phone)
            GlideAppModule.loadImage(applicationContext, hairstylistPictureUrl, hairstylistImage)



            recyclerView.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
            recyclerView.adapter = ServiceAdapter(this, hairStylist.services, date, hour, hairStylist.name)
        } else {
            Toast.makeText(applicationContext, "Datele despre frizerie nu au fost primite", Toast.LENGTH_SHORT).show()
        }
    }


}