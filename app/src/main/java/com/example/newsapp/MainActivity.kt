package com.example.newsapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.jsonData.ArtResponse
import com.example.newsapp.jsonData.MostPopularResponse
import com.example.newsappwithapi.NewsApi
import com.example.newsappwithapi.dataWeb.TopStoriesResponse

import com.example.recyclerview.ItemAdapter
import kotlinx.android.synthetic.main.activity_main.*


import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

var articleClicked = ArticleClicked()
var titleList = ArrayList<String>()
var dateList = ArrayList<String>()
var urlList = ArrayList<String>()
var pictureList = ArrayList<String>()
var categoryList = ArrayList<String>()

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val itemAdapter = ItemAdapter(this, title(), date(), cat(),picture(),url())
        getTopStories()
        val mbtnTopStories = findViewById(R.id.btnTopStories) as Button
        val mbtnMostPopular = findViewById(R.id.btnMostPopular) as Button
        val mbtnArts = findViewById(R.id.btnArts) as Button

        mbtnTopStories.setOnClickListener {
            itemAdapter.deleteItems()
            getTopStories()
        }

        mbtnMostPopular.setOnClickListener {
            itemAdapter.deleteItems()
            getMostPopular()
        }

        mbtnArts.setOnClickListener {
            try {


                itemAdapter.deleteItems()
                getArts()
            }
            catch (e: IOException) {
                e.printStackTrace()

            }



        }




    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean{

    val i = Intent(this@MainActivity, Search::class.java)

        when(item.itemId){
            R.id.miSearch ->
                startActivity(i)
                    //Toast.makeText(this, "You clicked on search", Toast.LENGTH_SHORT).show()
            R.id.miNotifications -> Toast.makeText(this, "You clicked on noifactions", Toast.LENGTH_SHORT).show()
            R.id.miContacts -> Toast.makeText(this, "You clicked on contacts", Toast.LENGTH_SHORT).show()

        }

        return true
    }

    fun getArts(): ArrayList<String>{
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




    fun getMostPopular(): ArrayList<String>{
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(NewsApi::class.java)

        val call = service.mostPopular()
        call.enqueue(object : Callback<MostPopularResponse> {

            override fun onResponse(call: Call<MostPopularResponse>, response: Response<MostPopularResponse>) {

                if (response.code() == 200) {
                    val newsResponse = response.body()!!

                    for (i in 1..newsResponse.results.size-1) {
                        titleList.add(newsResponse.results[i].title)
                    }
                    for (i in 1..newsResponse.results.size-1) {
                        dateList.add(newsResponse.results[i].published_date)
                    }
                    for (i in 1..newsResponse.results.size-1) {
                        urlList.add(newsResponse.results[i].url)
                    }
                    for (i in 0..6) {
                        if (newsResponse.results[0].media[0].media_metadata[0].url != "") {
                   //            pictureList.add(newsResponse.results[i].media[0].media_metadata[0].url)
                            pictureList.add(newsResponse.results[0].media[0].media_metadata[0].url)
                            pictureList.add(newsResponse.results[1].media[0].media_metadata[0].url)
                            pictureList.add(newsResponse.results[2].media[0].media_metadata[0].url)
                            pictureList.add(newsResponse.results[3].media[0].media_metadata[0].url)
                            pictureList.add(newsResponse.results[4].media[0].media_metadata[0].url)
                            pictureList.add(newsResponse.results[5].media[0].media_metadata[0].url)
                        }
                   }
                    for (i in 1..newsResponse.results.size-1) {
                        categoryList.add(newsResponse.results[i].section)

                    }

                    recyclerView()
                }


                println( "This is the title list " + titleList)
                println( "This is the date List  " + dateList)
                println( "This is the hyperlink List  " + urlList)
                println( "This is the category List  " + categoryList)
                println( "This is the image url  " + pictureList)



            }
            override fun onFailure(call: Call<MostPopularResponse>, t: Throwable) {
                //   newsData!!.text = t.message
            }
        })
        return titleList
    }

    fun getTopStories(): ArrayList<String>{
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(NewsApi::class.java)

            val call = service.topStories()
        call.enqueue(object : Callback<TopStoriesResponse> {

            override fun onResponse(call: Call<TopStoriesResponse>, response: Response<TopStoriesResponse>) {

                if (response.code() == 200) {
                    val newsResponse = response.body()!!

                    for (i in 1..newsResponse.results.size-1) {
                        titleList.add(newsResponse.results[i].title)
                    }
                    for (i in 1..newsResponse.results.size-1) {
                        dateList.add(newsResponse.results[i].published_date)
                    }
                    for (i in 1..newsResponse.results.size-1) {
                        urlList.add(newsResponse.results[i].url)
                    }
                    for (i in 1..newsResponse.results.size-1) {
                        pictureList.add(newsResponse.results[i].multimedia[0].url) // need to change

                    }
                    for (i in 1..newsResponse.results.size-1) {
                        categoryList.add(newsResponse.results[i].section)

                    }
                    recyclerView()
                }
            }
            override fun onFailure(call: Call<TopStoriesResponse>, t: Throwable) {
                //   newsData!!.text = t.message
            }
        })
        return titleList
    }





    fun recyclerView(){
        // Adapter class is initialized and list is passed in the param.

        val itemAdapter = ItemAdapter(this, title(), date(), cat(),picture(),url())

        recycler_view_items.layoutManager = LinearLayoutManager(this)

        // adapter instance is set to the recyclerview to inflate the items.
        recycler_view_items.adapter = itemAdapter
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
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu, menu)

        return true
    }




}