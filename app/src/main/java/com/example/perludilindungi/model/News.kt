package com.example.perludilindungi

data class NewsResponse(
    val count_total: Int,
    val message: String,
    val results: List<News>,
    val success: Boolean
)

data class News(
    val description: Description,
    val enclosure: Enclosure,
    val guid: String,
    val link: List<String>,
    val pubDate: String,
    val title: String
)

data class Description(
    val __cdata: String
)

data class Enclosure(
    val _length: String,
    val _type: String,
    val _url: String
)