package com.example.newsappwithapi

import com.example.newsapp.jsonData.MostPopularResponse
import com.example.newsappwithapi.dataWeb.TopStoriesResponse
import retrofit2.Call
import retrofit2.http.GET

interface NewsApi {
    @GET("mostpopular/v2/emailed/7.json?api-key=x2iWc8c8nV8F0MKCLZjxFSjjWx4JApsk")
    fun mostPopular(): Call<MostPopularResponse>

    @GET("topstories/v2/home.json?api-key=x2iWc8c8nV8F0MKCLZjxFSjjWx4JApsk")
    fun topStories(): Call<TopStoriesResponse>

    @GET("topstories/v2/arts.json?api-key=x2iWc8c8nV8F0MKCLZjxFSjjWx4JApsk")
    fun arts(): Call<TopStoriesResponse>

}
