package com.example.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class Search : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val mbtnSearch = findViewById(R.id.btnSearch) as Button

        mbtnSearch.setOnClickListener {
            searchQuery()
        }

    }


    fun searchQuery(){
        //Toast.makeText(this, "Search Button Worked", Toast.LENGTH_SHORT).show()

        var articleClicked = ArticleClicked()
        var titleList = ArrayList<String>()
        var dateList = ArrayList<String>()
        var urlList = ArrayList<String>()
        var pictureList = ArrayList<String>()
        var categoryList = ArrayList<String>()








    }





}