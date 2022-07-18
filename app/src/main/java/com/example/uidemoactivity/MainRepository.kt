package com.example.uidemoactivity

import com.example.uidemoactivity.retrofitBuilder.AirPollutionService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class MainRepository(private val airPollutionService: AirPollutionService) {

    suspend fun getAirPollutionData() = withContext(Dispatchers.IO) {
        if (airPollutionService.getAirPollutionDataSource().isSuccessful) {
            airPollutionService.getAirPollutionDataSource().body()
        } else {
            throw Exception(airPollutionService.getAirPollutionDataSource().message())
        }
    }
}