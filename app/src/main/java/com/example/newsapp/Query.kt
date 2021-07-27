package com.example.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.fragments.FragmentA
import com.example.myapplication.fragments.FragmentB

class Query : AppCompatActivity(), Communicator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_query)
        val fragmentA = FragmentA()
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragmentA).commit()
    }

    override fun passDataCom(editTextInput: String) {
        val bundle = Bundle()
        bundle.putString("message", editTextInput)
        val transaction = this.supportFragmentManager.beginTransaction()
        val fragmentB = FragmentB()
        fragmentB.arguments = bundle
        transaction.replace(R.id.fragmentContainer,fragmentB)
        transaction.commit()
    }

}