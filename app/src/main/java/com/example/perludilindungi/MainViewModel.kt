package com.example.perludilindungi

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.perludilindungi.model.CheckIn
import com.example.perludilindungi.model.CheckInResponse
import com.example.perludilindungi.model.FaskesResponse
import com.example.perludilindungi.model.NewsResponse
import com.example.perludilindungi.repository.Repository
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception

class MainViewModel constructor(private val repository: Repository) : ViewModel() {
    val list = MutableLiveData<NewsResponse>()
    val faskesList = MutableLiveData<FaskesResponse>()
    val failMsg = MutableLiveData<String>()
    val checkInResult = MutableLiveData<CheckInResponse>()

    fun getNews() {
        val result = repository.getNews()

        with(result) {
            enqueue(
                object : retrofit2.Callback<NewsResponse> {
                    override fun onResponse(
                        call: Call<NewsResponse>,
                        response: Response<NewsResponse>,
                    ) {
                        list.postValue(response.body())
                    }

                    override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                        failMsg.postValue(t.message)
                    }
                },
            )
        }
    }

    fun getFaskes(province: String, city: String) {
        val result = repository.getFaskes(province, city)

        with(result) {
            enqueue(
                object : retrofit2.Callback<FaskesResponse> {
                    override fun onResponse(
                        call: Call<FaskesResponse>,
                        response: Response<FaskesResponse>,
                    ) {
                        faskesList.postValue(response.body())
                    }

                    override fun onFailure(call: Call<FaskesResponse>, t: Throwable) {
                        failMsg.postValue(t.message)
                    }
                },
            )
        }
    }

    fun checkIn(qrCode: String, latitude: Double, longitude: Double){
        var checkIn = CheckIn(qrCode, latitude, longitude)
//        Log.d("what", "$latitude $longitude $qrCode")
        val result = repository.checkIn(checkIn)

        with(result) {
            enqueue(
                object : retrofit2.Callback<CheckInResponse> {
                    override fun onResponse(
                        call: Call<CheckInResponse>,
                        response: Response<CheckInResponse>
                    ) {

                        checkInResult.postValue(response.body())
                    }

                    override fun onFailure(call: Call<CheckInResponse>, t: Throwable) {
                        failMsg.postValue(t.message)
                    }

                }
            )
        }
    }
}