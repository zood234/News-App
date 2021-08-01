package com.example.myapplication.fragments

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.example.newsapp.*
import kotlinx.android.synthetic.main.fragment_a.*
import kotlinx.android.synthetic.main.fragment_a.view.*
import java.util.*


class FragmentA(searchOrNotification: String) : Fragment() {

    private lateinit var communicatior: Communicator
    var searchOrNotification = searchOrNotification
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_a, container, false)


        if (searchOrNotification != "search") {
            view.sendBtn.setVisibility(View.GONE)

        }

//        switchNotification.setOnCheckedChangeListener{ _, isChecked ->
//            println("YOU CLICKED THE NOTIFACTION")
//        }


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
        searchFilters.starDate = "20190101"
        println("start date " + searchFilters.starDate)
        // searchFilters.endDate = tvEndDate.text.toString()
        searchFilters.endDate = "20210707"
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
            val name = "Notifaction Title"
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
}