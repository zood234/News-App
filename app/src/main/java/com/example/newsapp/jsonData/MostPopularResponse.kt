package com.example.newsapp.jsonData

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

//https://api.nytimes.com/svc/mostpopular/v2/emailed/7.json?api-key=x2iWc8c8nV8F0MKCLZjxFSjjWx4JApsk

data class MostPopularResponse(
    val copyright: String,
    val num_results: Int,
    val results: List<Result>,
    val status: String
)

data class Result(
    val `abstract`: String,
    val adx_keywords: String,
    val asset_id: Long,
    val byline: String,
    val column: Any,
    val des_facet: List<String>,
    val eta_id: Int,
    val geo_facet: List<String>,
    val id: Long,
    val media: List<Media>,
    val nytdsection: String,
    val org_facet: List<String>,
    val per_facet: List<String>,
    val published_date: String,
    val section: String,
    val source: String,
    val subsection: String,
    val title: String,
    val type: String,
    val updated: String,
    val uri: String,
    val url: String
)

data class Media(
    val approved_for_syndication: Int,
    val caption: String,
    val copyright: String,

    @SerializedName("media-metadata")
    @Expose
    val media_metadata: List<MediaMetadata>,

    val subtype: String,
    val type: String
)

data class MediaMetadata(
    val format: String,
    val height: Int,
    val url: String,
    val width: Int
)