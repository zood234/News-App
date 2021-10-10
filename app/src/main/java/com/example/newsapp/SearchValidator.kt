package com.example.newsapp

import kotlinx.android.synthetic.main.fragment_a.*

//Checks to that the search is valid
class SearchValidator() {

     fun filtersActivated(artsFilter:Boolean, politicsFilter:Boolean, buisinessFilter:Boolean, sportsFilter:Boolean,
                                 entrepreneurFilter:Boolean, travelFilter:Boolean):Boolean{

        if  (artsFilter){
            return true
        }
        else if  (politicsFilter){
            return true
        }
        else if  (buisinessFilter){
            return true
        }
        else if  (sportsFilter){
            return true
        }
        else if  (entrepreneurFilter){
            return true
        }
        else return travelFilter
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

        return search != ""
    }
}