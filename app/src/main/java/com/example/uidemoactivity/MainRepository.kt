package com.example.uidemoactivity

import com.example.uidemoactivity.retrofitBuilder.RetrofitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepository(private val retrofitService: RetrofitService) {

    suspend fun getAirPollutionData() = withContext(Dispatchers.IO) {
        retrofitService.getAirPollutionDataSource()
    }
}