package com.example.newsapp.interfaces

import com.example.newsapp.models.jsonData.ArtResponse
import com.example.newsapp.models.jsonData.MostPopularResponse
import com.example.newsapp.models.jsonData.SearchResponse.QueryResponse
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

    @GET("search/v2/articlesearch.json?facet_filter=true")
    fun SearchQueryApi(@Query("q") search: String,
                       @Query("fq") arts: String,
                       @Query("fq") politics: String,
                       @Query("fq") bis: String,
                       @Query("fq") sports: String,
                       @Query("fq") entrepreneurs: String,
                       @Query("fq") travel: String,
                       @Query("begin_date") startDate: String,
                       @Query("end_date") endDate: String,
                       @Query("api-key") Key: String):
            Call<QueryResponse>
}