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
    ): View? {        val view = inflater.inflate(R.layout.fragment_a, container, false)


        view.sendBtn.setOnClickListener{

            communicatior = activity as Communicator
            communicatior.passDataCom(view.messageInput.text.toString())
            searchFilters.searchBox = view.messageInput.text.toString()


            // searchFilters.starDate = tvStartDate.text.toString()
            searchFilters.starDate = "20190101"
            println("start date " + searchFilters.starDate)
           // searchFilters.endDate = tvEndDate.text.toString()
            searchFilters.endDate = "20210707"
            println("end date " + searchFilters.endDate)

            if (cbArts.isChecked){
                searchFilters.arts = "Arts"
                println("Search filter " + searchFilters.arts)
            }
            if (cbPolitics.isChecked){
                searchFilters.politics = "Politics"
                println("Search filter " + searchFilters.politics)

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