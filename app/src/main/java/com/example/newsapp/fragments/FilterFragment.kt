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
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.newsapp.*
import com.example.newsapp.activities.alarmManager
import com.example.newsapp.activities.searchFilters
import com.example.newsapp.utils.Notification
import com.example.newsapp.interfaces.Communicator
import com.example.newsapp.utils.SearchValidator
import kotlinx.android.synthetic.main.fragment_a.*
import kotlinx.android.synthetic.main.fragment_a.view.*
import java.text.SimpleDateFormat
import java.util.*

class FilterFragment : Fragment() {
    private var textview_date: TextView? = null
    var cal = Calendar.getInstance()
    var startOrEndDate = ""
    private lateinit var communicatior: Communicator
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_a, container, false)
        resetFilters()
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
            view.switchNotification.visibility = View.GONE
        }

        if (notifactionOrSlider == "notification") {
            view.sendBtn.visibility = View.GONE
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
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
            }
        }

        view.switchNotification.setOnCheckedChangeListener { _, isChecked ->
            getfilters()
            defaultDate()
            val filtersVaild = filtersActivated(cbArts.isChecked,cbPolitics.isChecked,cbBusiness.isChecked,cbSports.isChecked,cbEntrepreneur.isChecked,cbTravel.isChecked)
            if (messageInput.text.toString() == "") {
                Toast.makeText(context, "You need to enter a search query", Toast.LENGTH_SHORT)
                    .show()
                view.switchNotification.isChecked = false
            } else if (filtersVaild == false) {
                Toast.makeText(
                    context,
                    "You need to select at least  one filters",
                    Toast.LENGTH_SHORT
                ).show()
                view.switchNotification.isChecked = false

            } else if (isChecked) {
                println("The Switch is on")
                createNotificationChannel()
                Toast.makeText(context, "Your notification is set", Toast.LENGTH_SHORT).show()

            } else {
                println("The Switch is off")
                cancelNotification()

            }

        }


        view.sendBtn.setOnClickListener {
            getfilters()
        val validator = SearchValidator()
            val filtersVaild = validator.filtersActivated(cbArts.isChecked,cbPolitics.isChecked,cbBusiness.isChecked,cbSports.isChecked,cbEntrepreneur.isChecked,cbTravel.isChecked)
            searchFilters.starDate = validator.getStartDate(etStartDate.text.toString())
            searchFilters.endDate = validator.getEndDate(etEndDate.text.toString())
         //   var filtersVaild = filtersActivated(cbArts.isChecked,cbPolitics.isChecked,cbBusiness.isChecked,cbSports.isChecked,cbEntrepreneur.isChecked,cbTravel.isChecked)
           val searchQuery = validator.getSearchQuery(messageInput.text.toString())

            if (searchQuery == false) {
                Toast.makeText(context, "You need to enter a search query", Toast.LENGTH_SHORT).show()
            }

            else if (filtersVaild == false) {
                Toast.makeText(context, "You need to select at least one filters", Toast.LENGTH_SHORT).show()
            }

            else {
               // Toast.makeText(context, "The New York Times API was successfully reached", Toast.LENGTH_SHORT).show()
                searchFilters.searchBox = view.messageInput.text.toString()
                communicatior = activity as Communicator
                communicatior.passDataCom(view.messageInput.text.toString())

            }
        }

        return view
    }

    private fun cancelNotification(){
        val intent = Intent(context, Notification()::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT)
        Log.d("MainActivity", "Delete : " + Date().toString())
        alarmManager.cancel((pendingIntent))
    }

    private fun defaultDate(){
        if (etStartDate.text.toString() == "") {
            searchFilters.starDate = "19000101"
        }
        else {
            searchFilters.starDate = etStartDate.text.toString()
            println("start date " + searchFilters.starDate)
        }
        if (etEndDate.text.toString() == "") {
            searchFilters.endDate = "20900707"
            println()
        }
        else {
            searchFilters.endDate = etEndDate.text.toString()
            println("end date " + searchFilters.endDate)
        }
    }
    private fun filtersActivated(artsFilter:Boolean, politicsFilter:Boolean, buisinessFilter:Boolean, sportsFilter:Boolean,
    entrepreneurFilter:Boolean, travelFilter:Boolean):Boolean{

        if  (artsFilter == true){
            return true
        }
        else if  (politicsFilter == true){
            return true
        }
        else if  (buisinessFilter == true){
            return true
        }
        else if  (sportsFilter == true){
            return true
        }
        else if  (entrepreneurFilter == true){
            return true
        }
        else return travelFilter == true
    }


    private fun createNotificationChannel() {
        val seconds = 31536000000
        val intent = Intent(context, Notification::class.java)
        val pendingIntent =
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        Log.d("MainActivity", "Create : " + Date().toString())
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + seconds, 31536000000, pendingIntent)
        val CHANNEL_ID = "chanel_id_example_01"
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
            etStartDate.setText(sdf.format(cal.time))
        }

        if(startOrEndDate == "end"){
            etEndDate.setText(sdf.format(cal.time))

        }
    }

    private fun getfilters(){

        if (cbArts.isChecked) {
            searchFilters.arts = "Arts"
        }
        if (cbPolitics.isChecked) {
            searchFilters.politics = "Politics"
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
    private fun resetFilters(){
            searchFilters.starDate = ""
            searchFilters.endDate = ""
            searchFilters.arts = ""
            searchFilters.politics = ""
            searchFilters.business = ""
            searchFilters.sports = ""
            searchFilters.entrepreneur = ""
            searchFilters.travel = ""

}

}

