package com.example.uidemoactivity.retrofitBuilder

import com.example.uidemoactivity.dataEntity.AirPollutionDataEntity
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RetrofitService {

    @GET("api/v2/aqx_p_432?limit=1000&api_key=39c55c3f-abfd-46c4-b992-71335900869c&sort=ImportDate%20desc&format=json")
    suspend fun getAirPollutionDataSource() : Response<AirPollutionDataEntity>

    companion object {
        private const val BASE_URL = "https://data.epa.gov.tw/"
        fun create() : RetrofitService {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(RetrofitService::class.java)

        }
    }
}