package com.example.uidemoactivity.viewModelFactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.uidemoactivity.MainRepository
import com.example.uidemoactivity.MainViewModel
import java.lang.IllegalArgumentException

class MainViewModelFactory(private val mainRepository: MainRepository): ViewModelProvider.Factory {

    companion object {
        @Volatile
        lateinit var mInstance: MainViewModelFactory

        fun getInstance(repository: MainRepository): MainViewModelFactory {
            if(!this::mInstance.isInitialized) {
                mInstance = MainViewModelFactory(repository)
            }
            return mInstance
        }
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(mainRepository) as T
            } else -> throw  IllegalArgumentException("UnKnown class")
        }
    }
}