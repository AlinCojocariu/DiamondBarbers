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

    private lateinit var clientName: EditText
    private lateinit var phoneNumber: EditText
    private lateinit var insertCode: EditText
    private lateinit var sendCode: Button
    private lateinit var verifyCode: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var storedVerificationId: String
    private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        clientName = findViewById(R.id.user_name)
        phoneNumber = findViewById(R.id.phone_number)
        insertCode = findViewById(R.id.insert_code)
        sendCode = findViewById(R.id.send_code_button)
        verifyCode = findViewById(R.id.check_code_button)


        FirebaseApp.initializeApp(this)
        auth = FirebaseAuth.getInstance()

        sendCode.setOnClickListener {
            startPhoneNumberVerification(phoneNumber.text.toString().trim())
            insertCode.visibility = View.VISIBLE
            verifyCode.visibility = View.VISIBLE
//            UserInformation.userInfo =
//                User(clientName.text.toString(), phoneNumber.text.toString())
//            startActivity(Intent(applicationContext, BarbershopsActivity::class.java))
        }

        verifyCode.setOnClickListener {
            verifyPhoneNumberWithCode(storedVerificationId, insertCode.text.toString().trim())
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
                resendToken = token
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
                        User(clientName.text.toString(), phoneNumber.text.toString())
                    startActivity(Intent(applicationContext, BarbershopsActivity::class.java))
                } else {
                    Toast.makeText(applicationContext, "Cod incorect", Toast.LENGTH_SHORT).show()
                }
            }
    }
}


