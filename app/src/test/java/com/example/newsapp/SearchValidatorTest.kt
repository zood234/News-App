package com.example.newsapp

import org.junit.Assert.*
import org.junit.Test

class SearchValidatorTest{
    var Validator = SearchValidator()

    @Test
    fun hasAtLeastOneFilterBeenActivated(){
    var filterEmpty = Validator.filtersActivated(true,false,false,false,false,false)
        assert(filterEmpty == true)
    }

    @Test
    fun isTheStartDateEmpty(){
       var startDate = Validator.getStartDate("")

       assertEquals("19000101",startDate)
    }

    @Test
    fun isTheEndDateEmpty(){
        var endDate = Validator.getEndDate("")

        assertEquals("20900707",endDate)
    }

    @Test
    fun isSearchEmpty(){
        var searchQuery = Validator.getSearchQuery("")
        assert(searchQuery == false)
    }

}