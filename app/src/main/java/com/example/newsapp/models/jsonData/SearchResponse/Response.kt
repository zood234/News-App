package com.example.newsapp.models.jsonData.SearchResponse

data class Response(
    val docs: List<Doc>,
    val facets: Facets,
    val meta: Meta
)