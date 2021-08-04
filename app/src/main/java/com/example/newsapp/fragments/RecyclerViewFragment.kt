package com.example.myapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.*
import com.example.newsapp.jsonData.SearchResponse.QueryResponse
import com.example.newsappwithapi.NewsApi
import com.example.recyclerview.ItemAdapter
import kotlinx.android.synthetic.main.fragment_b.*
import kotlinx.android.synthetic.main.fragment_b.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


class RecyclerViewFragment : Fragment() {

    var searchQuery: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_b, container, false)

        if( arguments?.getString("message") !=null) {
            searchQuery = arguments?.getString("message")!!
        }

        view.displaymsg.text = searchQuery
        searchQuery(titleList, dateList, categoryList, pictureList, urlList)
        return  view
    }

    private fun searchQuery(title: ArrayList<String?>, publishedDate: ArrayList<String>,
                            cat: ArrayList<String?>, picture: ArrayList<String?>, url: ArrayList<String?>
    ){

        val appContext = requireContext().applicationContext
        val itemAdapter = ItemAdapter(appContext, titleList, publishedDate, cat, pictureList,url)
        itemAdapter.deleteItems()
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
                            }
                            else {
                                pictureList.add("https://static01.nyt.com/" )
                            }

                        }
                        rvSearchQuery.layoutManager = LinearLayoutManager(appContext)
                        itemAdapter.notifyDataSetChanged()

                        // adapter instance is set to the recyclerview to inflate the items.
                        rvSearchQuery.adapter = itemAdapter
                    }
                }
                override fun onFailure(call: Call<QueryResponse>, t: Throwable) {
                }
            })}catch (e: IOException) {
            e.printStackTrace()
        }
    }

}