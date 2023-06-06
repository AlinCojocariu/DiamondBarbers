package com.example.diamondbarbers.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diamondbarbers.*
import com.example.diamondbarbers.adapters.CalendarAdapter
import com.example.diamondbarbers.models.Appointment
import com.example.diamondbarbers.models.CalendarSchedule
import com.example.diamondbarbers.models.HairStylist
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Period
import java.time.format.TextStyle
import java.util.*
import kotlin.collections.ArrayList

@Suppress("DEPRECATION")
class CalendarActivity : AppCompatActivity() {

    private lateinit var hairStylist : HairStylist

    private lateinit var recyclerView: RecyclerView

    private lateinit var mondayButton: TextView
    private lateinit var tuesdayButton: TextView
    private lateinit var wednesdayButton: TextView
    private lateinit var thursdayButton: TextView
    private lateinit var fridayButton: TextView
    private lateinit var saturdayButton: TextView
    private lateinit var sundayButton: TextView
    private lateinit var hairstylistName:TextView
    private lateinit var monthNameTextView: TextView

    @RequiresApi(Build.VERSION_CODES.O)
    private var startDate = LocalDate.now().with(DayOfWeek.MONDAY)
    private var currentWeek = 0L
    @RequiresApi(Build.VERSION_CODES.O)
    private var currentDate = LocalDate.now()

    private val LAST_WEEK_NUMBER = 51L      // 52 weeks. The number is 51 because we start from 0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        hairstylistName =findViewById(R.id.hairstylist_name)

        val bundle = intent.extras
        if(bundle != null) {
            hairStylist = bundle.getParcelable("hairstylist")!!
            hairstylistName.text= hairStylist?.name

        } else {
            Toast.makeText(applicationContext, "Datele despre frizer nu au fost primite", Toast.LENGTH_SHORT).show()
        }

        hairstylistName.setOnClickListener{
            val intent = Intent(applicationContext,HairstylistProfileActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable("hairstylist",hairStylist)
            intent.putExtras(bundle)
            startActivity(intent)

        }

        recyclerView = findViewById(R.id.recycler_view)

        monthNameTextView = findViewById(R.id.month_name)
        val rightArrowView = findViewById<View>(R.id.right_arrow)
        val leftArrowView = findViewById<View>(R.id.left_arrow)

        mondayButton = findViewById(R.id.monday_button)
        tuesdayButton = findViewById(R.id.tuesday_button)
        wednesdayButton = findViewById(R.id.wednesday_button)
        thursdayButton = findViewById(R.id.thursday_button)
        fridayButton = findViewById(R.id.friday_button)
        saturdayButton = findViewById(R.id.saturday_button)
        sundayButton = findViewById(R.id.sunday_button)

        updateUI(startDate)
        colorStartDate()
        updateCurrentDay()

        monthNameTextView.text = startDate.month.getDisplayName(TextStyle.FULL, Locale.getDefault())

        rightArrowView.setOnClickListener {

            leftArrowView.visibility = View.VISIBLE
            currentWeek++

            currentDate = currentDate.plusWeeks(1)
            updateCurrentDay()

            updateUI(startDate.plusWeeks(currentWeek))
            if(currentWeek == LAST_WEEK_NUMBER)
                rightArrowView.visibility = View.GONE
        }

        leftArrowView.visibility = View.GONE
        leftArrowView.setOnClickListener {
             rightArrowView.visibility = View.VISIBLE
             currentWeek--

            currentDate = currentDate.minusWeeks(1)
            updateCurrentDay()

             updateUI(startDate.plusWeeks(currentWeek))
            if(currentWeek == 0L)
                leftArrowView.visibility = View.GONE
        }

        mondayButton.setOnClickListener {
            clearAllButtons()
            mondayButton.setBackgroundColor(ContextCompat.getColor(applicationContext,
                R.color.rose
            ))

            currentDate = startDate.plusWeeks(currentWeek)
            updateCurrentDay()
        }

        tuesdayButton.setOnClickListener {
            clearAllButtons()
            tuesdayButton.setBackgroundColor(ContextCompat.getColor(applicationContext,
                R.color.rose
            ))

            currentDate = startDate.plusWeeks(currentWeek).plusDays(1)
            updateCurrentDay()
        }

        wednesdayButton.setOnClickListener {
            clearAllButtons()
            wednesdayButton.setBackgroundColor(ContextCompat.getColor(applicationContext,
                R.color.rose
            ))

            currentDate = startDate.plusWeeks(currentWeek).plusDays(2)
            updateCurrentDay()
        }

        thursdayButton.setOnClickListener {
            clearAllButtons()
            thursdayButton.setBackgroundColor(ContextCompat.getColor(applicationContext,
                R.color.rose
            ))

            currentDate = startDate.plusWeeks(currentWeek).plusDays(3)
            updateCurrentDay()
        }

        fridayButton.setOnClickListener {
            clearAllButtons()
            fridayButton.setBackgroundColor(ContextCompat.getColor(applicationContext,
                R.color.rose
            ))

            currentDate = startDate.plusWeeks(currentWeek).plusDays(4)
            updateCurrentDay()
        }

        saturdayButton.setOnClickListener {
            clearAllButtons()
            saturdayButton.setBackgroundColor(ContextCompat.getColor(applicationContext,
                R.color.rose
            ))

            currentDate = startDate.plusWeeks(currentWeek).plusDays(5)
            updateCurrentDay()
        }

        sundayButton.setOnClickListener {
            clearAllButtons()
            sundayButton.setBackgroundColor(ContextCompat.getColor(applicationContext,
                R.color.rose
            ))

            currentDate = startDate.plusWeeks(currentWeek).plusDays(6)
            updateCurrentDay()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()

        // Get the updated hairstylist
        // In case of an appointment, the new appointment should appear in the Calendar list
        if(UserInformation.currentHairStylist != null)
            hairStylist = UserInformation.currentHairStylist!!

        updateCurrentDay()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun colorStartDate() {
        val period = Period.between(startDate, currentDate)
        when(period.days) {
            0 -> mondayButton.setBackgroundColor(ContextCompat.getColor(applicationContext,
                R.color.rose
            ))
            1 -> tuesdayButton.setBackgroundColor(ContextCompat.getColor(applicationContext,
                R.color.rose
            ))
            2 -> wednesdayButton.setBackgroundColor(ContextCompat.getColor(applicationContext,
                R.color.rose
            ))
            3 -> thursdayButton.setBackgroundColor(ContextCompat.getColor(applicationContext,
                R.color.rose
            ))
            4 -> fridayButton.setBackgroundColor(ContextCompat.getColor(applicationContext,
                R.color.rose
            ))
            5 -> saturdayButton.setBackgroundColor(ContextCompat.getColor(applicationContext,
                R.color.rose
            ))
            6 -> sundayButton.setBackgroundColor(ContextCompat.getColor(applicationContext,
                R.color.rose
            ))
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateUI(date: LocalDate) {
        mondayButton.text = date.dayOfMonth.toString()
        tuesdayButton.text = date.plusDays(1).dayOfMonth.toString()
        wednesdayButton.text = date.plusDays(2).dayOfMonth.toString()
        thursdayButton.text = date.plusDays(3).dayOfMonth.toString()
        fridayButton.text = date.plusDays(4).dayOfMonth.toString()
        saturdayButton.text = date.plusDays(5).dayOfMonth.toString()
        sundayButton.text = date.plusDays(6).dayOfMonth.toString()

    }

    fun clearAllButtons() {
        mondayButton.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.black))
        tuesdayButton.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.black))
        wednesdayButton.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.black))
        thursdayButton.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.black))
        fridayButton.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.black))
        saturdayButton.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.black))
        sundayButton.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.black))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateCurrentDay() {
        monthNameTextView.text = currentDate.month.getDisplayName(TextStyle.FULL, Locale.getDefault())

        var list = getInitList(currentDate)
        list = updateAppointmentList(currentDate, list)

        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.adapter = CalendarAdapter(applicationContext, list, hairStylist)
    }

    fun getInitList(date: LocalDate): ArrayList<CalendarSchedule> {
        val list = ArrayList<CalendarSchedule>()
        var startHour = 9
        while (startHour < 20) {
            list.add(
                CalendarSchedule(
                    appointment = Appointment(date.toString(), "$startHour:00", "", "", ""),
                    reserved = false
                )
            )
            startHour++
        }
        return list
    }

    fun updateAppointmentList(date: LocalDate, list: ArrayList<CalendarSchedule>): ArrayList<CalendarSchedule> {
        for(appointment in hairStylist.appointments) {
            if(appointment.date == date.toString()) {
                for(schedule in list) {
                    if(schedule.appointment.hour == appointment.hour) {
                        schedule.appointment = appointment
                        schedule.reserved = true
                    }
                }
            }
        }
        return list
    }
}