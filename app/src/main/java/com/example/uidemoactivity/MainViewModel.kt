package com.example.uidemoactivity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.uidemoactivity.dataEntity.AirPollutionInfoEntity
import kotlinx.coroutines.*

class MainViewModel(application: Application, private val repository: MainRepository) :
    AndroidViewModel(application) {
    val errorMessage = MutableLiveData<String>()
    var topHorizontalInfoList = MutableLiveData<MutableList<AirPollutionInfoEntity>>()
    var downVerticalInfoList = MutableLiveData<MutableList<AirPollutionInfoEntity>>()
    private var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    fun startRequestAirPollutionDataSource() {
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = repository.getAirPollutionData()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    filterList(response.body()!!.mInfoEntity.filter {
                        it.mPMStatus.isNotEmpty()
                    }.toMutableList())
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }

    private fun filterList(mInfoEntity: MutableList<AirPollutionInfoEntity>) {
        var sum = 0f
        for (info in mInfoEntity) {
            sum += info.mPMStatus.toFloat()
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