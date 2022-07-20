package com.example.uidemoactivity

import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.example.uidemoactivity.dataEntity.AirPollutionInfo
import kotlinx.coroutines.*

class MainViewModel(private val repository: MainRepository) : ViewModel() {
    val errorMessage = MutableLiveData<String>()
    private var _topHorizontalInfoList = MutableLiveData<MutableList<AirPollutionInfo>>()
    var topHorizontalInfoList = _topHorizontalInfoList
    private var _downVerticalInfoList = MutableLiveData<MutableList<AirPollutionInfo>>()
    var downVerticalInfoList = _downVerticalInfoList
    var originVerticalInfoList = mutableListOf<AirPollutionInfo>()
    var searchText = ObservableField<String>()
    var showProgress = ObservableField(false)
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    fun startRequestAirPollutionDataSource() {
        viewModelScope.launch(exceptionHandler) {
            showProgress.set(true)
            filterList(repository.getAirPollutionData()!!.infoList.filter {
                it.pmStatus.isNotEmpty()
            }.toMutableList())
        }
    }

    private fun filterList(mInfoEntity: MutableList<AirPollutionInfo>) {
        var sum = mInfoEntity.sumOf {
            it.pmStatus.toDouble()
        }
        var avg = sum / mInfoEntity.size
        mInfoEntity.filter {
            it.pmStatus.toFloat() <= avg
        }.let {
            topHorizontalInfoList.postValue(it.toMutableList())
        }

        mInfoEntity.filter {
            it.pmStatus.toFloat() > avg
        }.let {
            downVerticalInfoList.postValue(it.toMutableList())
        }
        originVerticalInfoList = mInfoEntity
        showProgress.set(false)
    }

    private fun onError(message: String) {
        errorMessage.value = message
    }
}