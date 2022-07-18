package com.example.uidemoactivity.retrofitBuilder

import com.example.uidemoactivity.dataEntity.AirPollutionDataEntity
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RetrofitService {

    @GET("limit=1000&api_key=39c55c3f-abfd-46c4-b992-71335900869c&sort=ImportDate%20desc&format=json")
    fun getAirPollutionDataSource() : Response<AirPollutionDataEntity>

    companion object {
        var retrofitService: RetrofitService? = null
        fun getInstance() : RetrofitService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://data.epa.gov.tw/api/v2/aqx_p_432?")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}