package com.example.diamondbarbers.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diamondbarbers.Frizerie
import com.example.diamondbarbers.R
import com.example.diamondbarbers.adapters.HairStylistAdapter

@Suppress("DEPRECATION")
class HairStylistsActivity : AppCompatActivity() {

        private lateinit var recyclerView: RecyclerView
        private var barbershop : Frizerie?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hair_stylists)

        recyclerView =findViewById(R.id.HairstylistsRecyclerView)

        val bundle = intent.extras
        if(bundle != null) {
            barbershop = bundle.getParcelable("barbershop")

            intent.putExtras(bundle)
        } else {
            Toast.makeText(applicationContext, "Datele despre frizerie nu au fost primite", Toast.LENGTH_SHORT).show()
        }

        recyclerView.layoutManager = GridLayoutManager(applicationContext, 2)
        recyclerView.adapter = HairStylistAdapter(this@HairStylistsActivity,barbershop!!.hairStylists)


    }
}


