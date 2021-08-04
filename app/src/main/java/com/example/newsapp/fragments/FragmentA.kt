package com.example.myapplication.fragments

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.example.newsapp.*
import kotlinx.android.synthetic.main.fragment_a.*
import kotlinx.android.synthetic.main.fragment_a.view.*
import java.text.SimpleDateFormat
import java.util.*

class FragmentA(searchOrNotification: String) : Fragment() {
    var textview_date: TextView? = null
    var cal = Calendar.getInstance()
    var startOrEndDate = ""

    private lateinit var communicatior: Communicator
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_a, container, false)
        textview_date = this.etStartDate

        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int,
                                   dayOfMonth: Int) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }
        if (notifactionOrSlider == "search") {
            view.switchNotification.setVisibility(View.GONE)

        }

        if (notifactionOrSlider == "notification") {
            view.sendBtn.setVisibility(View.GONE)
        }

        view.etStartDate!!.setOnClickListener{
            startOrEndDate = "start"
                context?.let {
                    DatePickerDialog(
                        it,
                        dateSetListener,
                        // set DatePickerDialog to point to today's date when it loads up
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH)).show()
                }
            }



        view.etEndDate.setOnClickListener{
            startOrEndDate = "end"

            context?.let {
                DatePickerDialog(
                    it,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
            }

        }

        view.switchNotification.setOnCheckedChangeListener({ _, isChecked ->
            if (isChecked){
                println("The Switch is on")
                getfilters()
                createNotificationChannel()
            }
            else{
                println("The Switch is off")
                cancellNotification()

            }

        })


        view.sendBtn.setOnClickListener {
            getfilters()
            communicatior = activity as Communicator
            communicatior.passDataCom(view.messageInput.text.toString())
            searchFilters.searchBox = view.messageInput.text.toString()


        }

        return view
    }






    fun cancellNotification(){
        val intent = Intent(context, Receiver()::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT)
        Log.d("MainActivity", "Delete : " + Date().toString())
        alarmManager.cancel((pendingIntent))
    }

    fun getfilters(){
        searchFilters.starDate = etStartDate.text.toString() //"20190101"
        println("start date " + searchFilters.starDate)
        // searchFilters.endDate = tvEndDate.text.toString()
        searchFilters.endDate = etEndDate.text.toString()//"20210707"
        println("end date " + searchFilters.endDate)

        if (cbArts.isChecked) {
            searchFilters.arts = "Arts"
            println("Search filter " + searchFilters.arts)
        }
        if (cbPolitics.isChecked) {
            searchFilters.politics = "Politics"
            println("Search filter " + searchFilters.politics)

        }
        if (cbBusiness.isChecked) {
            searchFilters.business = "Business"
        }
        if (cbSports.isChecked) {
            searchFilters.sports = "Sports"
        }
        if (cbEntrepreneur.isChecked) {
            searchFilters.entrepreneur = "Entrepreneurs"
        }
        if (cbTravel.isChecked) {
            searchFilters.travel = "Travel"
        }
    }

    private fun createNotificationChannel() {
        val seconds = 31536000 * 1000 // change the 3 to 31536000 when i need to hand in
        val intent = Intent(context, Receiver::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        Log.d("MainActivity", "Create : " + Date().toString())
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + seconds, 1000 * 60 * 525600, pendingIntent)
        val CHANNEL_ID = "chanel_id_example_01"
        val notificationId = 101
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notification Title"
            val descriptionText = "Notification Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)


        }

    }
    private fun updateDateInView() {
        val myFormat = "yyyyMMdd" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)

        if(startOrEndDate == "start"){
            etStartDate.setText(sdf.format(cal.getTime()))
        }

        if(startOrEndDate == "end"){
            etEndDate.setText(sdf.format(cal.getTime()))

        }
    }
}