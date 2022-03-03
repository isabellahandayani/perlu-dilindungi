package com.example.perludilindungi.repository

import com.example.perludilindungi.model.CheckIn
import com.example.perludilindungi.network.RetrofitService

class Repository constructor(private val retrofitService: RetrofitService){

    fun getNews() = retrofitService.getNews()
    fun getFaskes(province: String, city: String) = retrofitService.getFaskes(province, city)
    fun getProvince() = retrofitService.getProvince()
    fun getCity(start_id : String) = retrofitService.getCity(start_id)
    fun checkIn(checkIn: CheckIn) = retrofitService.checkIn(checkIn)
}