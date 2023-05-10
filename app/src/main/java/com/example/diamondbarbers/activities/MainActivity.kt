package com.example.diamondbarbers.activities


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.diamondbarbers.*
import com.example.diamondbarbers.R
import com.google.firebase.database.*


class MainActivity : AppCompatActivity() {

    private lateinit var numeClient: EditText
    private lateinit var nrTelefon: EditText
    private lateinit var salveaza: Button
    private lateinit var databaseRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        numeClient = findViewById(R.id.numeClient)
        nrTelefon = findViewById(R.id.nrTelefon)
        salveaza = findViewById(R.id.Salveaza)
        databaseRef = FirebaseDatabase.getInstance().getReference("clients")
        salveaza.setOnClickListener {
            saveClient()

        }
    }

    private fun saveClient() {
        val name = numeClient.text.toString().trim()
        val phone = nrTelefon.text.toString().trim()

        if (name.isEmpty()) {
            numeClient.error = "insert you'r name"
            return
        }

        if (phone.isEmpty()) {
            nrTelefon.error = "insert phone number"
            return
        }

        if (name.isNotEmpty() && phone.isNotEmpty()) {

            val newRef = databaseRef.push()
            newRef.setValue(User(name, phone)).addOnCompleteListener {
                val userId = newRef.key
               // Toast.makeText(applicationContext, userId, Toast.LENGTH_LONG).show()
            }
            UserInformation.userInfo = User(name, phone)
            val intent = Intent(this, ListaFrizerii::class.java)

            val bundle = Bundle()
            intent.putExtras(bundle)
            startActivity(intent)

        }
//        //val clientId = databaseRef.push().key!!
//
//        val client = Client("4",name, phone)
//        databaseRef.child("4").setValue(client).addOnCompleteListener{
//            Toast.makeText(this,"salvat cu succes",Toast.LENGTH_LONG).show()
//        }
//            .addOnFailureListener{ err->
//                Toast.makeText(this,"eroare ${err.message}",Toast.LENGTH_LONG).show()
//            }

    }
}