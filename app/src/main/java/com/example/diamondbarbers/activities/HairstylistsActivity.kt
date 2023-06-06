package com.example.diamondbarbers.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diamondbarbers.models.Barbershop
import com.example.diamondbarbers.R
import com.example.diamondbarbers.adapters.HairstylistAdapter

@Suppress("DEPRECATION")
class HairstylistsActivity : AppCompatActivity() {

        private lateinit var recyclerView: RecyclerView
        private var barbershop : Barbershop?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hairstylists)

        recyclerView =findViewById(R.id.HairstylistsRecyclerView)

        val bundle = intent.extras
        if(bundle != null) {
            barbershop = bundle.getParcelable("barbershop")!!

            intent.putExtras(bundle)
        } else {
            Toast.makeText(applicationContext, "Datele despre frizerie nu au fost primite", Toast.LENGTH_SHORT).show()
        }

        recyclerView.layoutManager = GridLayoutManager(applicationContext, 2)
        recyclerView.adapter = HairstylistAdapter(this@HairstylistsActivity,barbershop!!.hairStylists)


    }
}


