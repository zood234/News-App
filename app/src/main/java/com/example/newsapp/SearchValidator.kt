package com.example.newsapp

import kotlinx.android.synthetic.main.fragment_a.*

class SearchValidator() {

     fun filtersActivated(artsFilter:Boolean, politicsFilter:Boolean, buisinessFilter:Boolean, sportsFilter:Boolean,
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

    fun getStartDate(startDate:String):String{
        if (startDate == ""){
            return "19000101"
        }
        return startDate
    }

    fun getEndDate(endDate:String):String{
        if (endDate == ""){
            return "20900707"
        }
        return endDate
    }

    fun getSearchQuery(search: String):Boolean {

        if (search == "") {
       return false
        }
        else {
            return true
        }
    }
}