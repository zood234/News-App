package com.example.newsapp.activities

import android.app.AlarmManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.fragments.FilterFragment
import com.example.myapplication.fragments.RecyclerViewFragment
import com.example.newsapp.R
import com.example.newsapp.interfaces.Communicator
import com.example.newsapp.utils.SearchFilters

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