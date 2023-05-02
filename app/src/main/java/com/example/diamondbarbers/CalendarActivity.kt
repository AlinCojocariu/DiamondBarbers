package com.example. diamondbarbers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import java.util.*

class CalendarActivity : AppCompatActivity() {

    private lateinit var hairStylist : HairStylist

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        var hairStylist_name =findViewById<TextView>(R.id.hairstylist_name)

        val bundle = intent.extras
        if(bundle != null) {
            hairStylist = bundle.getParcelable("hairstylist")!!
            hairStylist_name.text= "${hairStylist?.name}"
        } else {
            Toast.makeText(applicationContext, "Datele despre frizerie nu au fost primite", Toast.LENGTH_SHORT).show()
        }

        val monthNameTextView = findViewById<TextView>(R.id.month_name)
        val rightArrowView = findViewById<View>(R.id.right_arrow)
        val leftArrowView = findViewById<View>(R.id.left_arrow)

        val currentCalendar = Calendar.getInstance()
        val currentMonthName = currentCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
        monthNameTextView.text = currentMonthName

        val maxMonthsAhead = 3
        var currentMonth = currentCalendar.get(Calendar.MONTH)

        rightArrowView.setOnClickListener {
            currentMonth++
            if (currentMonth <= currentCalendar.get(Calendar.MONTH) + maxMonthsAhead) {
                val newCalendar = Calendar.getInstance()
                newCalendar.set(Calendar.MONTH, currentMonth)
                val newMonthName = newCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
                monthNameTextView.text = newMonthName
                // aici puteți adăuga și logica de afișare a zilelor corespunzătoare noii luni
            } else {
                currentMonth = currentCalendar.get(Calendar.MONTH) + maxMonthsAhead
            }
        }

        leftArrowView.setOnClickListener {

            if (currentMonth >= currentCalendar.get(Calendar.MONTH)) {
                val newCalendar = Calendar.getInstance()
                newCalendar.set(Calendar.MONTH, currentMonth--)
                val newMonthName = newCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
                monthNameTextView.text = newMonthName
                // aici puteți adăuga și logica de afișare a zilelor corespunzătoare noii luni
            } else {
                currentMonth = currentCalendar.get(Calendar.MONTH)
            }
        }










    }
}