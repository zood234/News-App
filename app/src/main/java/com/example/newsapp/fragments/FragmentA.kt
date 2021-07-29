package com.example.myapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

import com.example.newsapp.Communicator
import com.example.newsapp.R
import com.example.newsapp.searchFilters
import kotlinx.android.synthetic.main.fragment_a.*
import kotlinx.android.synthetic.main.fragment_a.view.*

class FragmentA : Fragment() {

private  lateinit var communicatior: Communicator
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_a, container, false)

        communicatior = activity as Communicator
//        var startDate = tvStartDate
//        var endDate = tvEndDate
//        var art = cbArts
//        var politics = cbPolitics
//        var business = cbBusiness
//        var sports = cbSports
//        var entrepreneur = cbEntrepreneur
//        var travel = cbTravel

        view.sendBtn.setOnClickListener{
           communicatior.passDataCom(view.messageInput.text.toString())
            searchFilters.searchBox = view.messageInput.text.toString()
            searchFilters.starDate = tvStartDate.text.toString()
            searchFilters.endDate = tvEndDate.text.toString()


            if (cbArts.isChecked){
                searchFilters.arts = "Arts"
            }
            if (cbPolitics.isChecked){
                searchFilters.politics = "Politics"
            }
            if (cbBusiness.isChecked){
                searchFilters.business = "Business"
            }
            if (cbSports.isChecked){
                searchFilters.sports = "Sports"
            }
            if (cbEntrepreneur.isChecked){
                searchFilters.entrepreneur = "Entrepreneurs"
            }
            if (cbTravel.isChecked){
                searchFilters.travel = "Travel"
            }



        }

        return  view
    }

}