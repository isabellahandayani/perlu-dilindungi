package com.example.perludilindungi.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.perludilindungi.model.*
import com.example.perludilindungi.repository.Repository
import retrofit2.Call
import retrofit2.Response

class MainViewModel constructor(private val repository: Repository) : ViewModel() {
    val list = MutableLiveData<NewsResponse>()
    val faskesList = MutableLiveData<FaskesResponse>()
    val failMsg = MutableLiveData<String>()

    val provinceList = MutableLiveData<ProvinceResponse>()
    val cityList = MutableLiveData<CityResponse>()
//    var provinceList = listOf<Province>()
//    var cityList = listOf<City>()

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

    fun getProvince() {
        val result = repository.getProvince()

        with(result) {
            enqueue(
                object : retrofit2.Callback<ProvinceResponse> {
                    override fun onResponse(
                        call: Call<ProvinceResponse>,
                        response: Response<ProvinceResponse>,
                    ) {
                        provinceList.postValue(response.body())
                    }

                    override fun onFailure(call: Call<ProvinceResponse>, t: Throwable) {
                        failMsg.postValue(t.message)
                    }
                },
            )
        }
    }

    fun getCity(start_id : String) {
        val result = repository.getCity(start_id)

        with(result) {
            enqueue(
                object : retrofit2.Callback<CityResponse> {
                    override fun onResponse(
                        call: Call<CityResponse>,
                        response: Response<CityResponse>,
                    ) {
                        cityList.postValue(response.body())
                    }

                    override fun onFailure(call: Call<CityResponse>, t: Throwable) {
                        failMsg.postValue(t.message)
                    }
                },
            )
        }
    }

}