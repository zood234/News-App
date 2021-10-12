package com.example.newsapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.newsapp.jsonData.SearchResponse.QueryResponse
import com.example.newsappwithapi.NewsApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.*

class Receiver : BroadcastReceiver(){
    val CHANNEL_ID = "chanel_id_example_01"
    val notificationId = 101

    override fun onReceive(context: Context, intent: Intent?) {

        searchQuery()
        //Checks to see if the top article is the same as the previous time the search was done
        while (titleList[0].toString() !== compareString){

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("There are new articles for your search query")
            .setContentText(titleList[0])
            .setPriority((NotificationCompat.PRIORITY_DEFAULT))

        with(NotificationManagerCompat.from(context)){
            notify(notificationId, builder.build())
            compareString = titleList[0].toString()
            Log.d("MainActivity", "Receiver : "+ Date().toString() + " titleList[0]: "+ titleList[0] + " compareString: " + compareString)

        }}

        if (titleList[0].toString() == compareString) {
            Log.d("MainActivity", "Receiver Was The Same: " + Date().toString())
        }

    }

//Runs a search for the query
    private fun searchQuery(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(NewsApi::class.java)
        val call = service.SearchQueryApi( searchFilters.searchBox,searchFilters.arts, searchFilters.politics,  searchFilters.business,
            searchFilters.sports, searchFilters.entrepreneur, searchFilters.travel, searchFilters.starDate,
            searchFilters.endDate, "x2iWc8c8nV8F0MKCLZjxFSjjWx4JApsk")

        try {


            call.enqueue(object : Callback<QueryResponse> {

                override fun onResponse(call: Call<QueryResponse>, response: Response<QueryResponse>) {

                    if (response.code() == 200) {
                        val newsResponse = response.body()!!


                        for (i in 1..newsResponse.response.docs.size-1) {
                            if (!newsResponse.response.docs[i].abstract.isEmpty()) {
                                titleList.add(newsResponse.response.docs[i].abstract)
                            }
                            if (!newsResponse.response.docs[i].pub_date.isEmpty()) {
                                dateList.add(newsResponse.response.docs[i].pub_date)
                            }

                            if (!newsResponse.response.docs[i].web_url.isEmpty()) {
                                urlList.add(newsResponse.response.docs[i].web_url)
                            }

                            if (!newsResponse.response.docs[i].section_name.isEmpty()) {
                                categoryList.add(newsResponse.response.docs[i].section_name)
                            }

                            if (newsResponse.response.docs[i].multimedia.size > 0  ) {
                                pictureList.add("https://static01.nyt.com/" +newsResponse.response.docs[i].multimedia[0].url)
                                println("1: https://static01.nyt.com/" +newsResponse.response.docs[i].multimedia[0].url)
                            }
                            else {

                                pictureList.add("https://static01.nyt.com/" )
                                println("It DID NOT WORK")
                            }

                        }

                    }
                }
                override fun onFailure(call: Call<QueryResponse>, t: Throwable) {
                }
            })}catch (e: IOException) {
            e.printStackTrace()

        }
    }
}