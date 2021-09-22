package com.example.newsapp

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myapplication.fragments.FilterFragment
import com.example.myapplication.fragments.RecyclerViewFragment
import java.util.*

var searchFilters = SearchFilters()
lateinit var alarmManager : AlarmManager

class Query : AppCompatActivity(), Communicator {

   override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_query)
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val fragmentA = FilterFragment() //search use this to activate button
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragmentA).commit()
           }


    override fun passDataCom(editTextInput: String) {
        val bundle = Bundle()
        bundle.putString("message", editTextInput)
        val transaction = this.supportFragmentManager.beginTransaction()
        val fragmentB = RecyclerViewFragment()
        fragmentB.arguments = bundle
        transaction.replace(R.id.fragmentContainer,fragmentB)
        transaction.commit()
    }

    private  fun createNotificationChannel(){
        val seconds = 3 *1000 // change the 3 to 31536000 when i need to hand in
        val intent = Intent(this, Receiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this,0,intent, PendingIntent.FLAG_UPDATE_CURRENT)
        Log.d("MainActivity", "Create : " + Date().toString())
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + seconds,1000 * 60 * 1, pendingIntent)// change the 1 to 525600
        val CHANNEL_ID = "chanel_id_example_01"
        val notificationId = 101
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = "Notifaction Title"
            val descriptionText = "Notification Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}