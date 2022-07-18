package com.example.uidemoactivity

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class MainViewModel(application: Application, private val repository: MainRepository) : AndroidViewModel(application) {
}