package com.example.diamondbarbers


import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.FirebaseAuth
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
    private lateinit var auth:FirebaseAuth
    private lateinit var storedVerificationId: String
    private lateinit var resedToken:PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks:PhoneAuthProvider.OnVerificationStateChangedCallbacks
    var wrongAttempts = 3


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       FirebaseApp.initializeApp(this)
        numeClient = findViewById(R.id.numeClient)
        nrTelefon = findViewById(R.id.nrTelefon)
        introducereCod = findViewById(R.id.introducereCod)
        sendcode = findViewById(R.id.trimitereCod)
        verifycode=findViewById(R.id.verificareCod)
        databaseRef = FirebaseDatabase.getInstance().getReference("clients")

            auth = FirebaseAuth.getInstance()


        
        sendcode.setOnClickListener {
            startPhoneNumberVerification(nrTelefon.text.toString().trim())
            introducereCod.visibility = View.VISIBLE
            verifycode.visibility = View.VISIBLE


        }

        verifycode.setOnClickListener{
            verifyPhoneNumberWithCode(storedVerificationId, introducereCod.text.toString().trim())

//            var lockoutTime: Date? = null
//            if(wrongAttempts!=0) {
//                wrongAttempts--
//                if (wrongAttempts == 0) {
//                    lockoutTime = Date()
//                    // Adaugă o oră la marcajul de timp pentru a stabili momentul în care pauza se termină
//                    lockoutTime!!.time +=  3 * 1000
//                    // Ascunde butonul
//                    introducereCod.visibility=View.GONE
//                    verifycode.visibility = View.GONE
//                }
//            }
//
//            //Log.w(TAG,"signInWithCredential:failure",task.exception)
//            // if(task.exception is FirebaseAuthInvalidCredentialsException){
//
//            //}
//            val handler = Handler()
//            handler.postDelayed({
//                if (lockoutTime != null && Date().after(lockoutTime)) {
//                    // Resetarea variabilelor
//                    wrongAttempts = 3
//                    lockoutTime = null
//                    // Afișarea butonului
//                    introducereCod.visibility=View.VISIBLE
//                    verifycode.visibility = View.VISIBLE
//                }
//            },3 * 1000)  // Verifică la fiecare oră
//
        }





        callbacks =  object  :  PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Log.d(TAG, "onVerificationCompleted:$credential")
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Log.w(TAG,"onVerificationFailed",e)

                if(e is FirebaseAuthInvalidCredentialsException)
                {}
                else if(e is FirebaseTooManyRequestsException)
                {}

        }

            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {

                Log.d(TAG,"onCodeSent:$verificationId")
                storedVerificationId= verificationId
                resedToken= token
            }

            }
    }

    override fun onStart() {
        super.onStart()
        //val currentUser=auth.currentUser
       // updateUI(currentUser)

//        var currentUser = auth.currentUser
//        if(currentUser != null) {
//            startActivity(Intent(applicationContext, ListaFrizerii::class.java))
//            finish()
//        }
//        auth.signOut()
    }

    private fun startPhoneNumberVerification(phoneNumber: String){
        val options =PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+4$phoneNumber")
            .setTimeout(60L,TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
    private fun verifyPhoneNumberWithCode(verificationId:String?,code:String)
    {
        val credential=PhoneAuthProvider.getCredential(verificationId!!,code)

        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential:PhoneAuthCredential){


        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) {task ->
                if(task.isSuccessful){
                    Log.d(TAG,"signInWithCredential:success")

                    //val user=task.result?.user
                    //Toast.makeText(this,"Welcome to the jungle :"+user,Toast.LENGTH_SHORT).show()
                    startActivity(Intent(applicationContext, ListaFrizerii::class.java))

                }
                else
                {
                    //Toast.makeText(applicationContext, "Cod incorect\nNumarul de incercari ramase este: $wrongAttempts", Toast.LENGTH_SHORT).show()
                    Toast.makeText(applicationContext, "Cod incorect", Toast.LENGTH_SHORT).show()
                }
            }
    }

//    private fun updateUI(user: FirebaseUser? =auth.currentUser)
//    {
//
//    }

    companion object{
        private const val TAG="MainActivity"
    }

}


