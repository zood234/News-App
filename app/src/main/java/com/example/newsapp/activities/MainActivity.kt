package com.example.newsapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.activities.Query
import com.example.newsapp.models.jsonData.ArtResponse
import com.example.newsapp.models.jsonData.MostPopularResponse
import com.example.newsapp.utils.ArticleClicked
import com.example.newsapp.interfaces.NewsApi
import com.example.newsappwithapi.dataWeb.TopStoriesResponse
import com.example.recyclerview.ItemAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

var articleClicked = ArticleClicked()
var compareString = ""
var notifactionOrSlider = ""
var titleList = ArrayList<String?>()
var dateList = ArrayList<String>()
var urlList = ArrayList<String?>()
var pictureList = ArrayList<String?>()
var categoryList = ArrayList<String?>()


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val itemAdapter = ItemAdapter(this, titleList, dateList, categoryList, pictureList, urlList)
        GlobalScope.launch {
            getTopStories()
        }
        val mbtnTopStories = findViewById<Button>(R.id.btnTopStories)
        val mbtnMostPopular = findViewById<Button>(R.id.btnMostPopular)
        val mbtnArts = findViewById<Button>(R.id.btnArts)

        mbtnTopStories.setOnClickListener {
            itemAdapter.deleteItems()
            GlobalScope.launch {
                getTopStories()
            }
        }

        mbtnMostPopular.setOnClickListener {
            itemAdapter.deleteItems()
            GlobalScope.launch {
                getMostPopular()
            }
        }

        mbtnArts.setOnClickListener {
                itemAdapter.deleteItems()
            GlobalScope.launch {
                getArts()
            }
        }
    }

    //Main menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean{
    val i = Intent(this@MainActivity, Query::class.java)

        when(item.itemId){
                    R.id.miSearch -> startSearch()
                R.id.miNotifications ->  startNotification()
            R.id.miHelp -> Toast.makeText(this, "For help please contact help@mynews.com", Toast.LENGTH_LONG).show()
            R.id.miAbouts -> Toast.makeText(this, "This app was created for a project on behalf of openclassroom", Toast.LENGTH_SHORT).show()

        }
        return true
    }

    //launches the search activity and turns it into notification option
    private fun startNotification(){
        notifactionOrSlider = "notification"
        val i = Intent(this@MainActivity, Query::class.java)
        startActivity(i)
    }

    //launches the search activity and turns it into search option
    private fun startSearch(){
        notifactionOrSlider = "search"
        val i = Intent(this@MainActivity, Query::class.java)
        startActivity(i)
    }

    //runs arts api and gets the data via arts and then populates the recycler view
    private suspend fun getArts(){
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
                       pictureList.add(newsResponse.results[i].multimedia[0].url)

                    }
                    for (i in 0..6) {
                      categoryList.add(newsResponse.results[i].section)

                    }
                    recyclerView(titleList,dateList, categoryList, pictureList, urlList)
                }

            }
            override fun onFailure(call: Call<ArtResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "There are network connectivity problems", Toast.LENGTH_LONG).show()
                    println("There are network connectivity problems")
            }
        })}catch (e: IOException) {
            e.printStackTrace()

        }
    }


    //runs most popular api and gets the data via arts and then populates the recycler view
    private suspend fun getMostPopular(){
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

                    for (i in 0..newsResponse.results.size-1) {
                        titleList.add(newsResponse.results[i].title)

                        dateList.add(newsResponse.results[i].published_date)

                        urlList.add(newsResponse.results[i].url)
//                            println("THE NEWS RESPONSE " + newsResponse.results[i].media[0].media_metadata[0].url)
//                        pictureList.add(newsResponse.results[i].media[0].media_metadata[0].url)

                        if (newsResponse.results[i].media.size > 0  ) {
                            pictureList.add(newsResponse.results[i].media[0].media_metadata[0].url)
                        }
                        else {
                            pictureList.add("https://static01.nyt.com/" )
                        }

                        categoryList.add(newsResponse.results[i].section)

                    }

                    recyclerView(titleList,dateList, categoryList, pictureList, urlList)
                }
            }
            override fun onFailure(call: Call<MostPopularResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "There are network connectivity problems", Toast.LENGTH_LONG).show()
                println("There are network connectivity problems")
            }
        })
        return
    }

    //runs top stories api and gets the data via arts and then populates the recycler view
    private suspend fun getTopStories(){
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
                    recyclerView(titleList,dateList, categoryList, pictureList, urlList)
                }
            }
            override fun onFailure(call: Call<TopStoriesResponse>, t: Throwable) {
                Toast.makeText(applicationContext, "There are network connectivity problems", Toast.LENGTH_LONG).show()
                println("There are network connectivity problems")
            }
        })
    }



    // creates the recycler view and populates it with the data provided by arrays
    fun recyclerView( title: ArrayList<String?>,  publishedDate: ArrayList<String>,
         cat: ArrayList<String?>,  picture: ArrayList<String?>, url: ArrayList<String?>
    ){
        // Adapter class is initialized and list is passed in the param.
        val itemAdapter = ItemAdapter(this, title, publishedDate, cat,picture,url)
        recycler_view_items.layoutManager = LinearLayoutManager(this)
        recycler_view_items.adapter = itemAdapter
        //updates the recycler view
        itemAdapter.notifyDataSetChanged()
    }




    //inflates the menu
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu, menu)

        return true
    }
}