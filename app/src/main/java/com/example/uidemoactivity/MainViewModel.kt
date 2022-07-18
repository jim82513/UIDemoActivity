package com.example.uidemoactivity

import android.app.Application
import androidx.lifecycle.*
import com.example.uidemoactivity.dataEntity.AirPollutionInfoEntity
import kotlinx.coroutines.*

class MainViewModel(private val repository: MainRepository) : ViewModel() {
    val errorMessage = MutableLiveData<String>()
    private var _topHorizontalInfoList = MutableLiveData<MutableList<AirPollutionInfoEntity>>()
    var topHorizontalInfoList = _topHorizontalInfoList
    private var _downVerticalInfoList = MutableLiveData<MutableList<AirPollutionInfoEntity>>()
    var downVerticalInfoList = _downVerticalInfoList
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    fun startRequestAirPollutionDataSource() {
        viewModelScope.launch {
            val response = repository.getAirPollutionData()
            if (response.isSuccessful) {
                filterList(response.body()!!.mInfoEntity.filter {
                    it.mPMStatus.isNotEmpty()
                }.toMutableList())
            } else {
                onError("Error : ${response.message()} ")
            }
        }
    }

    private fun filterList(mInfoEntity: MutableList<AirPollutionInfoEntity>) {
        var sum = mInfoEntity.sumOf {
            it.mPMStatus.toDouble()
        }

        var avg = sum / mInfoEntity.size
        mInfoEntity.filter {
            it.mPMStatus.toFloat() <= avg
        }.let {
            topHorizontalInfoList.postValue(it.toMutableList())
        }

        mInfoEntity.filter {
            it.mPMStatus.toFloat() > avg
        }.let {
            downVerticalInfoList.postValue(it.toMutableList())
        }
    }

    private fun onError(message: String) {
        errorMessage.value = message
    }
}