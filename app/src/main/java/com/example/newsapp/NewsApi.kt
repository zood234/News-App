package com.example.newsappwithapi

import com.example.newsappwithapi.dataWeb.NewsResponse
import retrofit2.Call
import retrofit2.http.GET

interface NewsApi {
    @GET("topstories/v2/home.json?api-key=x2iWc8c8nV8F0MKCLZjxFSjjWx4JApsk")
    fun getNews(): Call<NewsResponse>
}
