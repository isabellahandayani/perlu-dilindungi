package com.example.perludilindungi.viewmodels

import android.location.Location
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.perludilindungi.model.*
import com.example.perludilindungi.model.CheckIn
import com.example.perludilindungi.model.CheckInResponse
import com.example.perludilindungi.model.FaskesResponse
import com.example.perludilindungi.model.NewsResponse
import com.example.perludilindungi.repository.Repository
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception
import java.lang.IllegalArgumentException

class MainViewModel constructor(private val repository: Repository) : ViewModel() {
    val list = MutableLiveData<NewsResponse>()
    val faskesList = MutableLiveData<FaskesResponse>()
    val failMsg = MutableLiveData<String>()
    val checkInResult = MutableLiveData<CheckInResponse>()

    val provinceList = MutableLiveData<ProvinceResponse>()
    val cityList = MutableLiveData<CityResponse>()

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

    fun getFaskes(province: String, city: String, sortedByLocation: Boolean=false, latitude: Double =0.0, longitude: Double=0.0) {
        val result = repository.getFaskes(province, city)

        with(result) {
            enqueue(
                object : retrofit2.Callback<FaskesResponse> {
                    override fun onResponse(
                        call: Call<FaskesResponse>,
                        response: Response<FaskesResponse>,
                    ) {

                        var responseBody = response.body()

                        if (sortedByLocation) {
                            var sortedFaskes = responseBody?.data?.sortedBy { it.latitude?.let { it1 ->
                                it.longitude?.let { it2 ->
                                    calculateDistance(latitude,longitude,
                                        it1.toDouble(), it2.toDouble())
                                }
                            } }
                            if (responseBody != null) {
                                if (sortedFaskes != null) {
                                    responseBody.data = sortedFaskes.take(5)
                                }
                            }
                        }
                        faskesList.postValue(responseBody!!)
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

    fun calculateDistance(lat1: Double, lng1: Double, lat2: Double, lng2: Double): Float {
        val results = FloatArray(1)
        Location.distanceBetween(lat1, lng1, lat2, lng2, results)
        // distance in meter
        return results[0]

    }
}

class ViewModelFactory constructor(private val repository: Repository) : ViewModelProvider.Factory {

    override  fun <T : ViewModel> create(modelClass: Class<T>) : T {
        return if(modelClass.isAssignableFrom(MainViewModel::class.java)) MainViewModel(this.repository) as T else {
            throw IllegalArgumentException("Viewmodel not found")
        }
    }
}