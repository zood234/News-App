package com.example.newsappwithapi

import com.example.newsapp.jsonData.ArtResponse
import com.example.newsapp.jsonData.MostPopularResponse
import com.example.newsapp.jsonData.SearchResponse.Doc
import com.example.newsapp.jsonData.SearchResponse.QueryResponse
import com.example.newsappwithapi.dataWeb.TopStoriesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("mostpopular/v2/emailed/7.json?api-key=x2iWc8c8nV8F0MKCLZjxFSjjWx4JApsk")
    fun mostPopular(): Call<MostPopularResponse>

    @GET("topstories/v2/home.json?api-key=x2iWc8c8nV8F0MKCLZjxFSjjWx4JApsk")
    fun topStories(): Call<TopStoriesResponse>

    @GET("topstories/v2/arts.json?api-key=x2iWc8c8nV8F0MKCLZjxFSjjWx4JApsk")
    fun arts(): Call<ArtResponse>

   @GET("search/v2/articlesearch.json?fq=trump&fq=travel&facet_field=day_of_week&facet=true&begin_date=20200101&end_date=20210101&api-key=x2iWc8c8nV8F0MKCLZjxFSjjWx4JApsk")
   fun search(): Call<QueryResponse>

    @GET("search/v2/articlesearch.json")
    fun SearchQueryApi( @Query("fq") search: String, @Query("api-key") Key: String):Call<QueryResponse>
}
