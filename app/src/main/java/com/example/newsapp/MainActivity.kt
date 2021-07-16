package com.example.newsapp

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsappwithapi.NewsApi
import com.example.newsappwithapi.dataWeb.NewsResponse
import com.example.recyclerview.ItemAdapter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL


var titleList = ArrayList<String>()
var dateList = ArrayList<String>()
var urlList = ArrayList<String>()
var pictureList = ArrayList<String>()
var categoryList = ArrayList<String>()

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getApiData()

    }

    fun getApiData(): ArrayList<String>{
        //This one gets added

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(NewsApi::class.java)
        val call = service.getNews()
        call.enqueue(object : Callback<NewsResponse> {

            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {


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
            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean{

        when(item.itemId){
            R.id.miSearch -> Toast.makeText(this, "You clicked on search", Toast.LENGTH_SHORT).show()
            R.id.miNotifications -> Toast.makeText(this, "You clicked on noifactions", Toast.LENGTH_SHORT).show()
            R.id.miContacts -> Toast.makeText(this, "You clicked on contacts", Toast.LENGTH_SHORT).show()

        }

        return true
    }


}