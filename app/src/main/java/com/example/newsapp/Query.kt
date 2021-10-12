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


}