package com.example.perludilindungi.network

import com.example.perludilindungi.model.CheckIn
import com.example.perludilindungi.model.CheckInResponse
import com.example.perludilindungi.model.NewsResponse
import com.example.perludilindungi.model.FaskesResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RetrofitService {

    @GET("api/get-news")
    fun getNews() : Call<NewsResponse>

    @GET("api/get-faskes-vaksinasi")
    fun getFaskes(@Query("province")  province: String, @Query("city") city: String ) : Call<FaskesResponse>

    @POST("check-in")
    fun checkIn(@Body checkIn: CheckIn) : Call<CheckInResponse>

    companion object {
        var retrofitService: RetrofitService? = null
        fun getInstance() : RetrofitService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://perludilindungi.herokuapp.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}