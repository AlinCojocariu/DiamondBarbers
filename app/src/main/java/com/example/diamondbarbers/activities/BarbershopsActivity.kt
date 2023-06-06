package com.example.diamondbarbers.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diamondbarbers.R
import com.example.diamondbarbers.adapters.BarbershopAdapter
import com.example.diamondbarbers.models.*
import com.google.firebase.database.*

class BarbershopsActivity : AppCompatActivity() {


    private var barberShops = arrayListOf<Barbershop>()
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_barbershops)

        recyclerView=findViewById(R.id.frizeriiRecyclerView)

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
                        val serviceList = mutableListOf<Service>()
                        for (serviceSnapshot in hairstylistSnapshot.child("services").children){
                            val type = serviceSnapshot.child("type").value as String
                            val price = serviceSnapshot.child("price").value as String
                            val time = serviceSnapshot.child("time").value as String
                            val service = Service (price,time,type)
                            serviceList.add(service)
                        }
                        val appointmentsList = mutableListOf<Appointment>()
                        for(appointmentsSnapshot in hairstylistSnapshot.child("programari").children){
                            val date = appointmentsSnapshot.child("date").value as String
                            val hour = appointmentsSnapshot.child("hour").value as String
                            val appointmentName = appointmentsSnapshot.child("name").value as String
                            val appointmentPhone = appointmentsSnapshot.child("phone").value as String
                            val type = appointmentsSnapshot.child("type").value as String
                            val appointment = Appointment(date,hour,appointmentName,appointmentPhone,type)
                            appointmentsList.add(appointment)

                        }
                        val productsList= mutableListOf<Product>()
                         hairstylistSnapshot.child("products").children.forEachIndexed { productIndex, productSnapshot ->
                            val productName=productSnapshot.child("name").value.toString()
                            val product= Product(productIndex,productName)
                            productsList.add(product)
                        }
                        val gallery= mutableListOf<String>()
                        for(gallerySnapshot in hairstylistSnapshot.child("gallery").children){
                            val photo=gallerySnapshot.child("photo").value as String
                            gallery.add(photo)
                        }

                        val hairStylist = HairStylist(hairstylistIndex, hairstylistName,image,phone,serviceList,appointmentsList,productsList,gallery)
                        hairstylistList.add(hairStylist)
                    }
                   val barberShop = Barbershop(barberShopIndex, name,address,city,hairstylistList)
                    barberShops.add(barberShop)

                }
                recyclerView.layoutManager = LinearLayoutManager(applicationContext)
                recyclerView.adapter= BarbershopAdapter(applicationContext,barberShops)
            }


            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext,error.toString(),Toast.LENGTH_SHORT).show()
            }

        })




    }
}