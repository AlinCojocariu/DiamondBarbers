package com.example.diamondbarbers.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diamondbarbers.*
import com.example.diamondbarbers.R
import com.example.diamondbarbers.adapters.FrizerieAdapter
import com.google.firebase.database.*

class ListaFrizerii : AppCompatActivity() {


    private var barberShops = arrayListOf<Frizerie>()
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_frizerii)

        recyclerView=findViewById(R.id.frizeriiRecyclerView)

        val bundle = intent.extras
        if(bundle != null) {

            intent.putExtras(bundle)
        } else {
            Toast.makeText(applicationContext, "Numarul de telefon sau numele nu poate fi luat", Toast.LENGTH_SHORT).show()
        }

       // Toast.makeText(applicationContext, phone, Toast.LENGTH_SHORT).show()
        val frizeriiRef = FirebaseDatabase.getInstance().getReference("barber-shops")
        frizeriiRef.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                Toast.makeText(applicationContext, "${snapshot.childrenCount}", Toast.LENGTH_SHORT).show()
                snapshot.children.forEachIndexed { barberShopIndex, barbershopSnapshot ->
                    val name = barbershopSnapshot.child("name").value as String
                    val address = barbershopSnapshot.child("address").value as String
                    val city = barbershopSnapshot.child("city").value as String
                    val hairstylistList = mutableListOf<HairStylist>()
                    barbershopSnapshot.child("hairstylists").children.forEachIndexed { hairstylistIndex, hairstylistSnapshot ->
                        val hairstylistName = hairstylistSnapshot.child("name").value as String
                        val image = hairstylistSnapshot.child("image").value as String
                        val phone = hairstylistSnapshot.child("phone").value as String
                        val servicesList = mutableListOf<Services>()
                        for (serviceSnapshot in hairstylistSnapshot.child("services").children){
                            val type = serviceSnapshot.child("type").value as String
                            val price = serviceSnapshot.child("price").value as String
                            val time = serviceSnapshot.child("time").value as String
                            val service = Services (price,time,type)
                            servicesList.add(service)
                        }
                        val appointmentsList = mutableListOf<Appointments>()
                        for(appointmentsSnapshot in hairstylistSnapshot.child("programari").children){
                            val date = appointmentsSnapshot.child("date").value as String
                            val hour = appointmentsSnapshot.child("hour").value as String
                            val appointmentName = appointmentsSnapshot.child("name").value as String
                            val appointmentPhone = appointmentsSnapshot.child("phone").value as String
                            val type = appointmentsSnapshot.child("type").value as String
                            val appointment = Appointments(date,hour,appointmentName,appointmentPhone,type)
                            appointmentsList.add(appointment)

                        }
                        val productsList= mutableListOf<Products>()
                         hairstylistSnapshot.child("products").children.forEachIndexed { productIndex, productSnapshot ->
                            val productName=productSnapshot.child("name").value.toString()
                            val product=Products(productIndex,productName)
                            productsList.add(product)
                        }
                        val gallery= mutableListOf<String>()
                        for(gallerySnapshot in hairstylistSnapshot.child("gallery").children){
                            val photo=gallerySnapshot.child("photo").value as String
                            gallery.add(photo)
                        }

                        val hairStylist = HairStylist(hairstylistIndex, hairstylistName,image,phone,servicesList,appointmentsList,productsList,gallery)
                        hairstylistList.add(hairStylist)
                    }
                   val barberShop = Frizerie(barberShopIndex, name,address,city,hairstylistList)
                    barberShops.add(barberShop)

                }
                recyclerView.layoutManager = LinearLayoutManager(applicationContext)
                recyclerView.adapter= FrizerieAdapter(applicationContext,barberShops)
            }


            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext,error.toString(),Toast.LENGTH_SHORT).show()
            }

        })




    }
}