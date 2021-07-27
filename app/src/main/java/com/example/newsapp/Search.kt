package com.example.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.jsonData.ArtResponse
import com.example.newsappwithapi.NewsApi
import com.example.recyclerview.ItemAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_search.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class Search : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val mbtnSearch = findViewById(R.id.btnSearch) as Button

        mbtnSearch.setOnClickListener {
            searchQuery()
        }

    }


    fun searchQuery():ArrayList<String>{
        //Toast.makeText(this, "Search Button Worked", Toast.LENGTH_SHORT).show()

        var articleClicked = ArticleClicked()
        var titleList = ArrayList<String>()
        var dateList = ArrayList<String>()
        var urlList = ArrayList<String>()
        var pictureList = ArrayList<String>()
        var categoryList = ArrayList<String>()



        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(NewsApi::class.java)
        val call = service.arts()
        try {


            call.enqueue(object : Callback<ArtResponse> {

                override fun onResponse(call: Call<ArtResponse>, response: Response<ArtResponse>) {

                    if (response.code() == 200) {
                        val newsResponse = response.body()!!

                        for (i in 0..5) {
                            titleList.add(newsResponse.results[i].title)
                        }
                        for (i in 0..5) {
                            dateList.add(newsResponse.results[i].published_date)
                        }
                        for (i in 0..5) {
                            urlList.add(newsResponse.results[i].url)
                        }
                        for (i in 0..5) {
                            pictureList.add(newsResponse.results[i].multimedia[0].url) // need to change

                        }
                        for (i in 0..6) {
                            categoryList.add(newsResponse.results[i].section)

                        }
                        recyclerView()
                    }
                }
                override fun onFailure(call: Call<ArtResponse>, t: Throwable) {
                    //   newsData!!.text = t.message
                }
            })}catch (e: IOException) {
            e.printStackTrace()

        }
        return titleList
    }



    fun recyclerView(){
        // Adapter class is initialized and list is passed in the param.

        val itemAdapter = ItemAdapter(this, title(), date(), cat(),picture(),url())
        rvSearch.layoutManager = LinearLayoutManager(this)

        // adapter instance is set to the recyclerview to inflate the items.
        rvSearch.adapter = itemAdapter
        itemAdapter.notifyDataSetChanged()
        //  itemAdapter.deleteItems()
    }
    fun title(): ArrayList<String>{
        return titleList
    }

    fun date(): ArrayList<String>{
        return dateList
    }

    fun url(): ArrayList<String>{
        return urlList
    }

    fun cat(): ArrayList<String>{
        return categoryList
    }

    fun picture(): ArrayList<String>{
        return pictureList
    }








}