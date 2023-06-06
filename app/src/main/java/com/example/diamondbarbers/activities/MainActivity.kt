package com.example.diamondbarbers.activities


import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.FirebaseAuth
import com.example.diamondbarbers.*
import com.example.diamondbarbers.R
import com.example.diamondbarbers.models.User
import com.google.firebase.database.*
import java.util.*
import java.util.concurrent.TimeUnit
class MainActivity : AppCompatActivity() {

    private lateinit var numeClient: EditText
    private lateinit var nrTelefon: EditText
    private lateinit var introducereCod: EditText
    private lateinit var sendcode: Button
    private lateinit var verifycode: Button
    private lateinit var databaseRef: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var storedVerificationId: String
    private lateinit var resedToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        numeClient = findViewById(R.id.numeClient)
        nrTelefon = findViewById(R.id.nrTelefon)
        introducereCod = findViewById(R.id.introducereCod)
        sendcode = findViewById(R.id.trimitereCod)
        verifycode = findViewById(R.id.verificareCod)

        databaseRef = FirebaseDatabase.getInstance().getReference("clients")

        FirebaseApp.initializeApp(this)
        auth = FirebaseAuth.getInstance()

        sendcode.setOnClickListener {
            startPhoneNumberVerification(nrTelefon.text.toString().trim())
            introducereCod.visibility = View.VISIBLE
            verifycode.visibility = View.VISIBLE
        }

        verifycode.setOnClickListener {
            verifyPhoneNumberWithCode(storedVerificationId, introducereCod.text.toString().trim())
        }


        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                if (e is FirebaseAuthInvalidCredentialsException) {
                } else if (e is FirebaseTooManyRequestsException) {
                }
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                storedVerificationId = verificationId
                resedToken = token
            }

        }
    }

    private fun startPhoneNumberVerification(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+4$phoneNumber")
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun verifyPhoneNumberWithCode(verificationId: String?, code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    UserInformation.userInfo =
                        User(numeClient.text.toString(), nrTelefon.text.toString())
                    startActivity(Intent(applicationContext, BarbershopsActivity::class.java))
                } else {
                    Toast.makeText(applicationContext, "Cod incorect", Toast.LENGTH_SHORT).show()
                }
            }
    }
}


