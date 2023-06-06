package com.example.diamondbarbers.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.diamondbarbers.models.GlideAppModule
import com.example.diamondbarbers.models.HairStylist
import com.example.diamondbarbers.R
import com.example.diamondbarbers.adapters.GalleryAdapter
import com.example.diamondbarbers.adapters.ProductAdapter

class HairstylistProfileActivity : AppCompatActivity() {

    private lateinit var recyclerViewProducts: RecyclerView
    private lateinit var hairstylistName:TextView
    private lateinit var hairstylistPhone:TextView
    private lateinit var hairstylistPicture:ImageView
    private lateinit var hairstylist: HairStylist

    private lateinit var gallery:ViewPager



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hairstylist_profile)

        recyclerViewProducts=findViewById(R.id.productsRecycler)
        hairstylistName=findViewById(R.id.hairstylist_name)
        hairstylistPhone=findViewById(R.id.hairstylist_phone)
        hairstylistPicture=findViewById(R.id.hairstylist_picture)

        gallery=findViewById(R.id.photos)


        val bundle=intent.extras
        if(bundle != null) {
            hairstylist = bundle.getParcelable("hairstylist")!!

            intent.putExtras(bundle)
        } else {
            Toast.makeText(applicationContext, "Datele despre frizer nu au fost primite", Toast.LENGTH_SHORT).show()
        }
        hairstylistName.text=hairstylist.name
        hairstylistPhone.text=hairstylist.phone
        GlideAppModule.loadImage(applicationContext, hairstylist.image,hairstylistPicture)

        recyclerViewProducts.layoutManager = LinearLayoutManager(applicationContext)
        recyclerViewProducts.adapter=ProductAdapter(applicationContext,hairstylist.products)

        val adapter=GalleryAdapter(this,hairstylist.gallery)
        gallery.adapter=adapter





    }
}