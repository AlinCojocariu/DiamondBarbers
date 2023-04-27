package com.example.diamondbarbers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HairStylistsActivity : AppCompatActivity() {

        private var pictureList = arrayListOf<String>()
        private lateinit var recyclerView: RecyclerView
        private var barbershop : Frizerie?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hair_stylists)

        recyclerView =findViewById(R.id.HairstylistsRecyclerView)

        val bundle = intent.extras
        if(bundle != null) {
            barbershop = bundle.getParcelable("barbershop")
        } else {
            Toast.makeText(applicationContext, "Datele despre frizerie nu au fost primite", Toast.LENGTH_SHORT).show()
        }

        recyclerView.layoutManager = GridLayoutManager(applicationContext, 2)
        recyclerView.adapter = HairStylistAdapter(this@HairStylistsActivity,barbershop!!.hairStylists)


    }
}


